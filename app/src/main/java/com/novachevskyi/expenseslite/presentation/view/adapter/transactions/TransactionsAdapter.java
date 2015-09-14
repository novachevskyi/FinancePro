package com.novachevskyi.expenseslite.presentation.view.adapter.transactions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import java.util.Collection;
import java.util.List;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TransactionsAdapter
    extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

  public interface OnItemClickListener {
    void onTransactionItemClicked(TransactionModel transactionModel);
  }

  private OnItemClickListener onItemClickListener;

  private List<TransactionModel> transactionsCollection;
  private LayoutInflater layoutInflater;
  private final Context context;

  public TransactionsAdapter(Context context, Collection<TransactionModel> transactionsCollection) {
    this.context = context;
    if (context != null) {
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    this.transactionsCollection = (List<TransactionModel>) transactionsCollection;
  }

  @Override public int getItemCount() {
    return (this.transactionsCollection != null) ? this.transactionsCollection.size() : 0;
  }

  @Override public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (this.layoutInflater != null) {
      View view =
          this.layoutInflater.inflate(R.layout.recycler_view_item_transaction, parent, false);

      return new TransactionViewHolder(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(TransactionViewHolder holder, final int position) {
    final TransactionModel transactionModel = this.transactionsCollection.get(position);

    DateTimeFormatter formatterDayMonthYear = DateTimeFormat.forPattern(
        context.getString(R.string.adapter_transactions_month_day_year_template));
    if (transactionModel.getPaymentDate() != null) {
      String formattedDateDayMonthYear =
          formatterDayMonthYear.print(transactionModel.getPaymentDate());
      holder.tv_date.setText(formattedDateDayMonthYear);
    } else {
      holder.tv_date.setText("");
    }

    if (transactionModel.getType() == TransactionType.INCOME) {
      holder.iv_type.setImageResource(R.drawable.ic_in_arrow);
    } else {
      holder.iv_type.setImageResource(R.drawable.ic_out_arrow);
    }

    if (TextUtils.isEmpty(transactionModel.getPayee())) {
      holder.tv_payee_header.setVisibility(View.GONE);
      holder.tv_payee.setVisibility(View.GONE);
    } else {
      holder.tv_payee_header.setVisibility(View.VISIBLE);
      holder.tv_payee.setVisibility(View.VISIBLE);
      holder.tv_payee.setText(transactionModel.getPayee());

      if (transactionModel.getType() == TransactionType.INCOME) {
        holder.tv_payee_header.setText(
            context.getString(R.string.adapter_transactions_source_header));
      } else {
        holder.tv_payee_header.setText(
            context.getString(R.string.adapter_transactions_payee_header));
      }
    }

    if (TextUtils.isEmpty(transactionModel.getNotes())) {
      holder.tv_notes_header.setVisibility(View.GONE);
      holder.tv_notes.setVisibility(View.GONE);
    } else {
      holder.tv_notes_header.setVisibility(View.VISIBLE);
      holder.tv_notes.setVisibility(View.VISIBLE);
      holder.tv_notes.setText(transactionModel.getNotes());
    }

    holder.tv_name.setText(Category.getCategoryTitleExpanded(transactionModel.getCategory(), context));

    holder.tv_amount.setText(String.format(
        context.getString(R.string.adapter_transactions_amount_text_template),
        transactionModel.getAmount()));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (TransactionsAdapter.this.onItemClickListener != null) {
          TransactionsAdapter.this.onItemClickListener.onTransactionItemClicked(transactionModel);
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

  public void setTransactionsCollection(Collection<TransactionModel> transactionsCollection) {
    this.transactionsCollection = (List<TransactionModel>) transactionsCollection;
    this.notifyDataSetChanged();
  }

  public void addTransactionsCollection(final Collection<TransactionModel> transactionsCollection) {
    this.transactionsCollection.addAll(transactionsCollection);
    this.notifyDataSetChanged();
  }

  static class TransactionViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tv_name) TextView tv_name;
    @InjectView(R.id.tv_date) TextView tv_date;
    @InjectView(R.id.tv_amount) TextView tv_amount;
    @InjectView(R.id.iv_type) ImageView iv_type;

    @InjectView(R.id.tv_payee_header) TextView tv_payee_header;
    @InjectView(R.id.tv_payee) TextView tv_payee;

    @InjectView(R.id.tv_notes) TextView tv_notes;
    @InjectView(R.id.tv_notes_header) TextView tv_notes_header;

    public TransactionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
