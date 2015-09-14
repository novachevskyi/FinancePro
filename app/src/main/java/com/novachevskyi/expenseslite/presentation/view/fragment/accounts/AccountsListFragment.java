package com.novachevskyi.expenseslite.presentation.view.fragment.accounts;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.presenter.accounts.AccountsListPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.adapter.accounts.AccountsAdapter;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountsLoadView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseListFragment;
import java.util.List;

public class AccountsListFragment extends BaseListFragment implements AccountsLoadView {

  private AccountsListPresenter accountsListPresenter;

  private AccountsAdapter accountsAdapter;

  private ViewGroup topContainer;

  public static AccountsListFragment newInstance() {
    AccountsListFragment splashFragment = new AccountsListFragment();

    Bundle argumentsBundle = new Bundle();
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  @Override protected String getEmptyText() {
    return getContext().getString(R.string.fragment_accounts_list_empty_text);
  }

  @Override protected void initializeViewStubHeader(ViewGroup container) {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      topContainer = container;

      constructAccountsListUpperBanner(new OnAdViewLoadListener() {
        @Override public void onLoad(AdView adView) {
          if (topContainer != null) {
            topContainer.removeAllViews();
            topContainer.addView(adView);
          }
        }
      });
    }
  }

  @Override protected void reloadData() {
    AccountsListFragment.this.accountsListPresenter.loadData();
  }

  @Override protected void onScrolled(RecyclerView recyclerView, int dx, int dy,
      ListLayoutManager layoutManager) {
    AccountsListFragment.this.accountsListPresenter.listScrolled(layoutManager);
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
        this.accountsAdapter = new AccountsAdapter(getActivity(), accounts);
        setAdapter(accountsAdapter);
      } else {
        if (isInitialLoad) {
          this.accountsAdapter.setAccountsCollection(accounts);
        } else {
          this.accountsAdapter.addAccountsCollection(accounts);
        }
      }

      this.accountsAdapter.setOnItemClickListener(new AccountsAdapter.OnItemClickListener() {
        @Override public void onAccountItemClicked(AccountModel accountModel) {
          getNavigator().navigateToTransactionsByAccountListScreen(getActivity(),
              accountModel.getAccountId());
        }
      });
    }
  }
}
