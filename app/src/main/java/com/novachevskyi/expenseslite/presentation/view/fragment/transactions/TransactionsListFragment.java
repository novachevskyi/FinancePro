package com.novachevskyi.expenseslite.presentation.view.fragment.transactions;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.presenter.transactions.TransactionsListPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.adapter.transactions.TransactionsAdapter;
import com.novachevskyi.expenseslite.presentation.view.base.transactions.TransactionsLoadView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseListFragment;
import java.util.List;
import org.joda.time.LocalDate;

public class TransactionsListFragment extends BaseListFragment implements TransactionsLoadView {

  private static final String TRANSACTION_FILTER_FRAGMENT_TYPE_KEY =
      "transaction_filter_fragment_type_key";
  private static final String TRANSACTION_MONTH_FRAGMENT_TYPE_KEY =
      "transaction_month_fragment_type_key";
  private static final String TRANSACTION_ACCOUNT_ID_FRAGMENT_TYPE_KEY =
      "transaction_account_id_fragment_type_key";
  private static final String TRANSACTION_BUDGET_ID_FRAGMENT_TYPE_KEY =
      "transaction_budget_id_fragment_type_key";

  private TransactionsListPresenter transactionsListPresenter;

  private TransactionsAdapter transactionsAdapter;

  private TransactionFilterType transactionFilterType;
  private LocalDate transactionMonthFilter;
  private String transactionAccountIdFilter;
  private String transactionBudgetIdFilter;

  private ViewGroup topContainer;

  public static TransactionsListFragment newInstance() {
    TransactionsListFragment splashFragment = new TransactionsListFragment();

    Bundle argumentsBundle = new Bundle();
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  public static TransactionsListFragment newInstance(int transactionFilterType) {
    TransactionsListFragment splashFragment = new TransactionsListFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putInt(TRANSACTION_FILTER_FRAGMENT_TYPE_KEY, transactionFilterType);
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  public static TransactionsListFragment newInstance(int transactionFilterType, int month) {
    TransactionsListFragment splashFragment = new TransactionsListFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putInt(TRANSACTION_FILTER_FRAGMENT_TYPE_KEY, transactionFilterType);
    argumentsBundle.putInt(TRANSACTION_MONTH_FRAGMENT_TYPE_KEY, month);
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  public static TransactionsListFragment newInstance(int transactionFilterType, String id) {
    TransactionsListFragment splashFragment = new TransactionsListFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putInt(TRANSACTION_FILTER_FRAGMENT_TYPE_KEY, transactionFilterType);
    if (transactionFilterType == TransactionFilterType.ACCOUNT.getNumericType()) {
      argumentsBundle.putString(TRANSACTION_ACCOUNT_ID_FRAGMENT_TYPE_KEY, id);
    } else if (transactionFilterType == TransactionFilterType.BUDGET.getNumericType()) {
      argumentsBundle.putString(TRANSACTION_BUDGET_ID_FRAGMENT_TYPE_KEY, id);
    }
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  private void initialize() {
    Bundle bundle = this.getArguments();
    int transactionTypeIntValue = bundle.getInt(TRANSACTION_FILTER_FRAGMENT_TYPE_KEY, -1);
    transactionFilterType =
        transactionTypeIntValue < 0 ? null : TransactionFilterType.getTransactionFilterType(
            transactionTypeIntValue);

    int transactionMonthIntValue = bundle.getInt(TRANSACTION_MONTH_FRAGMENT_TYPE_KEY, -1);
    if (transactionMonthIntValue < 0) {
      transactionMonthFilter = null;
    } else {
      LocalDate localDate = new LocalDate();
      transactionMonthFilter =
          new LocalDate(localDate.getYear(), transactionMonthIntValue + 1,
              transactionMonthIntValue + 1);
    }

    if (transactionFilterType == TransactionFilterType.ACCOUNT) {
      transactionAccountIdFilter = bundle.getString(TRANSACTION_ACCOUNT_ID_FRAGMENT_TYPE_KEY);
    } else if (transactionFilterType == TransactionFilterType.BUDGET) {
      transactionBudgetIdFilter = bundle.getString(TRANSACTION_BUDGET_ID_FRAGMENT_TYPE_KEY);
    }
  }

  @Override protected String getEmptyText() {
    return getContext().getString(R.string.fragment_transactions_list_empty_text);
  }

  @Override protected void initializeViewStubHeader(ViewGroup container) {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      topContainer = container;

      constructTransactionsListUpperBanner(new OnAdViewLoadListener() {
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
    TransactionsListFragment.this.transactionsListPresenter.loadData();
  }

  @Override protected void onScrolled(RecyclerView recyclerView, int dx, int dy,
      ListLayoutManager layoutManager) {
    TransactionsListFragment.this.transactionsListPresenter.listScrolled(layoutManager);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.transactionsListPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.transactionsListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.transactionsListPresenter.pause();
  }

  @Override protected void initializePresenter() {
    initialize();

    if (transactionMonthFilter != null) {
      this.transactionsListPresenter =
          new TransactionsListPresenter(this, transactionFilterType, transactionMonthFilter);
    } else if (transactionAccountIdFilter != null) {
      this.transactionsListPresenter =
          new TransactionsListPresenter(this, transactionFilterType, transactionAccountIdFilter);
    } else if (transactionBudgetIdFilter != null) {
      this.transactionsListPresenter =
          new TransactionsListPresenter(this, transactionFilterType, transactionBudgetIdFilter);
    } else {
      this.transactionsListPresenter =
          new TransactionsListPresenter(this, transactionFilterType);
    }
  }

  @Override public void transactionsLoaded(List<TransactionModel> transactionModels,
      boolean isInitialLoad) {
    if (transactionModels != null) {
      if (this.transactionsAdapter == null) {
        this.transactionsAdapter = new TransactionsAdapter(getActivity(), transactionModels);
        setAdapter(transactionsAdapter);
      } else {
        if (isInitialLoad) {
          this.transactionsAdapter.setTransactionsCollection(transactionModels);
        } else {
          this.transactionsAdapter.addTransactionsCollection(transactionModels);
        }
      }
    }
  }
}
