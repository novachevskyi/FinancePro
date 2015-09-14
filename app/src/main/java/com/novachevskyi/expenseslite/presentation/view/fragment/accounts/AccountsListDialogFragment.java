package com.novachevskyi.expenseslite.presentation.view.fragment.accounts;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.presenter.accounts.AccountsListPresenter;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.adapter.accounts.AccountsDialogAdapter;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountsLoadView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseListDialogFragment;
import java.util.List;

public class AccountsListDialogFragment extends BaseListDialogFragment implements AccountsLoadView {

  private AccountsListPresenter accountsListPresenter;

  private AccountsDialogAdapter accountsAdapter;

  public static AccountsListDialogFragment newInstance() {
    AccountsListDialogFragment accountsListDialogFragment = new AccountsListDialogFragment();

    Bundle argumentsBundle = new Bundle();
    accountsListDialogFragment.setArguments(argumentsBundle);

    return accountsListDialogFragment;
  }

  @Override protected String getEmptyText() {
    return getContext().getString(R.string.fragment_accounts_list_dialog_empty_text);
  }

  @Override protected void onScrolled(RecyclerView recyclerView, int dx, int dy,
      ListLayoutManager layoutManager) {
    AccountsListDialogFragment.this.accountsListPresenter.listScrolled(layoutManager);
  }

  @Override protected void reloadData() {
    AccountsListDialogFragment.this.accountsListPresenter.loadData();
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.accountsListPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.accountsListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.accountsListPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.accountsListPresenter =
        new AccountsListPresenter(this);
  }

  @Override public void accountsLoaded(List<AccountModel> accounts, boolean isInitialLoad) {
    if (accounts != null) {
      if (this.accountsAdapter == null) {
        this.accountsAdapter = new AccountsDialogAdapter(getActivity(), accounts);
        setAdapter(this.accountsAdapter);
      } else {
        if (isInitialLoad) {
          this.accountsAdapter.setAccountsCollection(accounts);
        } else {
          this.accountsAdapter.addAccountsCollection(accounts);
        }
      }

      accountsAdapter.setOnItemClickListener(new AccountsDialogAdapter.OnItemClickListener() {
        @Override public void onAccountItemClicked(AccountModel accountModel) {
          AccountsListDialogFragment.this.onItemClickListener.onAccountItemClicked(accountModel);
        }
      });
    }
  }

  public interface OnItemClickListener {
    void onAccountItemClicked(AccountModel accountModel);
  }

  private OnItemClickListener onItemClickListener;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public void dataValidated() {

  }
}
