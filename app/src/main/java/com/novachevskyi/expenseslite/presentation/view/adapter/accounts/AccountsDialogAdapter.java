package com.novachevskyi.expenseslite.presentation.view.adapter.accounts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import java.util.Collection;
import java.util.List;

public class AccountsDialogAdapter extends RecyclerView.Adapter<AccountsDialogAdapter.AccountViewHolder> {

  public interface OnItemClickListener {
    void onAccountItemClicked(AccountModel accountModel);
  }

  private OnItemClickListener onItemClickListener;

  private List<AccountModel> accountsCollection;
  private LayoutInflater layoutInflater;
  private final Context context;

  public AccountsDialogAdapter(Context context, Collection<AccountModel> accountsCollection) {
    this.context = context;
    if (context != null) {
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    this.accountsCollection = (List<AccountModel>) accountsCollection;
  }

  @Override public int getItemCount() {
    return (this.accountsCollection != null) ? this.accountsCollection.size() : 0;
  }

  @Override public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (this.layoutInflater != null) {
      View view = this.layoutInflater.inflate(R.layout.recycler_view_item_dialog_account, parent, false);

      return new AccountViewHolder(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(AccountViewHolder holder, final int position) {
    final AccountModel accountModel = this.accountsCollection.get(position);

    holder.tv_name.setText(accountModel.getName());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (AccountsDialogAdapter.this.onItemClickListener != null) {
          AccountsDialogAdapter.this.onItemClickListener.onAccountItemClicked(accountModel);
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

  public void setAccountsCollection(Collection<AccountModel> accountsCollection) {
    this.accountsCollection = (List<AccountModel>) accountsCollection;
    this.notifyDataSetChanged();
  }

  public void addAccountsCollection(final Collection<AccountModel> accountsCollection) {
    this.accountsCollection.addAll(accountsCollection);
    this.notifyDataSetChanged();
  }

  static class AccountViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tv_name) TextView tv_name;

    public AccountViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
