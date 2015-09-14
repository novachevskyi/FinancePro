package com.novachevskyi.expenseslite.presentation.view.adapter.reports;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.reports.MonthReportModel;
import com.novachevskyi.expenseslite.presentation.view.component.BaseReportLayoutView;
import java.util.Collection;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ReportsMonthAdapter
    extends RecyclerView.Adapter<ReportsMonthAdapter.ReportViewHolder> {

  public interface OnItemClickListener {
    void onReportItemClicked(MonthReportModel reportModel);
  }

  private OnItemClickListener onItemClickListener;

  private List<MonthReportModel> reportsCollection;
  private LayoutInflater layoutInflater;
  private final Context context;

  public ReportsMonthAdapter(Context context, Collection<MonthReportModel> reportsCollection) {
    this.context = context;
    if (context != null) {
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    this.reportsCollection = (List<MonthReportModel>) reportsCollection;
  }

  @Override public int getItemCount() {
    return (this.reportsCollection != null) ? this.reportsCollection.size() : 0;
  }

  @Override public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (this.layoutInflater != null) {
      View view =
          this.layoutInflater.inflate(R.layout.recycler_view_item_report, parent, false);

      return new ReportViewHolder(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(ReportViewHolder holder, final int position) {
    final MonthReportModel reportModel = this.reportsCollection.get(position);

    DateTimeFormatter formatter = DateTimeFormat.forPattern(
        context.getString(R.string.adapter_reports_month_month_template));
    LocalDate today = new LocalDate();
    LocalDate month =
        new LocalDate(today.getYear(), reportModel.getMonth() + 1, today.getDayOfMonth());
    String formattedDate = formatter.print(month);
    holder.tv_title.setText(formattedDate);

    holder.v_month_report.setMonthReportObject(reportModel.getMonthReport());

    holder.rl_details.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (ReportsMonthAdapter.this.onItemClickListener != null) {
          ReportsMonthAdapter.this.onItemClickListener.onReportItemClicked(reportModel);
        }
      }
    });
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setReportsCollection(Collection<MonthReportModel> reportsCollection) {
    this.reportsCollection = (List<MonthReportModel>) reportsCollection;
    this.notifyDataSetChanged();
  }

  public void addReportsCollection(final Collection<MonthReportModel> reportsCollection) {
    this.reportsCollection.addAll(reportsCollection);
    this.notifyDataSetChanged();
  }

  static class ReportViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tv_title) TextView tv_title;
    @InjectView(R.id.v_month_report) BaseReportLayoutView v_month_report;
    @InjectView(R.id.rl_details) RelativeLayout rl_details;

    public ReportViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
