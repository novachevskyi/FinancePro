package com.novachevskyi.expenseslite.presentation.view.adapter.budgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import com.novachevskyi.expenseslite.presentation.view.component.BudgetReportLayoutView;
import java.util.Collection;
import java.util.List;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BudgetsAdapter
    extends RecyclerView.Adapter<BudgetsAdapter.BudgetViewHolder> {

  public interface OnItemClickListener {
    void onBudgetItemClicked(BudgetModel budgetModel);
  }

  private OnItemClickListener onItemClickListener;

  private List<BudgetModel> budgetsCollection;
  private LayoutInflater layoutInflater;
  private final Context context;

  public BudgetsAdapter(Context context, Collection<BudgetModel> budgetsCollection) {
    this.context = context;
    if (context != null) {
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    this.budgetsCollection = (List<BudgetModel>) budgetsCollection;
  }

  @Override public int getItemCount() {
    return (this.budgetsCollection != null) ? this.budgetsCollection.size() : 0;
  }

  @Override public BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (this.layoutInflater != null) {
      View view =
          this.layoutInflater.inflate(R.layout.recycler_view_item_budget, parent, false);

      return new BudgetViewHolder(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(BudgetViewHolder holder, final int position) {
    final BudgetModel budgetModel = this.budgetsCollection.get(position);

    DateTimeFormatter formatterDayMonth = DateTimeFormat.forPattern(
        context.getString(R.string.adapter_budgets_month_day_template));
    String formattedDateDayMonth = formatterDayMonth.print(budgetModel.getDateFrom());

    DateTimeFormatter formatterDayMonthYear = DateTimeFormat.forPattern(
        context.getString(R.string.adapter_budgets_month_day_year_template));
    String formattedDateDayMonthYear = formatterDayMonthYear.print(budgetModel.getDateTo());

    holder.tv_period.setText(String.format("%s - %s", formattedDateDayMonth,
        formattedDateDayMonthYear));

    if (TextUtils.isEmpty(budgetModel.getNotes())) {
      holder.tv_notes_header.setVisibility(View.GONE);
      holder.tv_notes.setVisibility(View.GONE);
    } else {
      holder.tv_notes_header.setVisibility(View.VISIBLE);
      holder.tv_notes.setVisibility(View.VISIBLE);
      holder.tv_notes.setText(budgetModel.getNotes());
    }

    holder.tv_title.setText(Category.getCategoryTitle(
        budgetModel.getCategory(), context));

    holder.v_report.setMonthReportObject(budgetModel.getStartAmount(), budgetModel.getAmount());

    holder.rl_details.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (BudgetsAdapter.this.onItemClickListener != null) {
          BudgetsAdapter.this.onItemClickListener.onBudgetItemClicked(budgetModel);
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

  public void setBudgetsCollection(Collection<BudgetModel> budgetsCollection) {
    this.budgetsCollection = (List<BudgetModel>) budgetsCollection;
    this.notifyDataSetChanged();
  }

  public void addBudgetsCollection(final Collection<BudgetModel> budgetsCollection) {
    this.budgetsCollection.addAll(budgetsCollection);
    this.notifyDataSetChanged();
  }

  static class BudgetViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tv_title) TextView tv_title;
    @InjectView(R.id.tv_period) TextView tv_period;
    @InjectView(R.id.v_report) BudgetReportLayoutView v_report;
    @InjectView(R.id.rl_details) RelativeLayout rl_details;

    @InjectView(R.id.tv_notes) TextView tv_notes;
    @InjectView(R.id.tv_notes_header) TextView tv_notes_header;

    public BudgetViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
