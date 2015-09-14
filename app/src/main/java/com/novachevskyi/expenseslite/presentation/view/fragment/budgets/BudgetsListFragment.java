package com.novachevskyi.expenseslite.presentation.view.fragment.budgets;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.presenter.budgets.BudgetsListPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.adapter.budgets.BudgetsAdapter;
import com.novachevskyi.expenseslite.presentation.view.base.budgets.BudgetsLoadView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseListFragment;
import java.util.List;

public class BudgetsListFragment extends BaseListFragment implements BudgetsLoadView {

  private BudgetsListPresenter budgetsListPresenter;

  private BudgetsAdapter budgetsAdapter;

  private ViewGroup topContainer;

  public static BudgetsListFragment newInstance() {
    BudgetsListFragment splashFragment = new BudgetsListFragment();

    Bundle argumentsBundle = new Bundle();
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  @Override protected String getEmptyText() {
    return getContext().getString(R.string.fragment_budgets_list_empty_text);
  }

  @Override protected void initializeViewStubHeader(ViewGroup container) {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      topContainer = container;

      constructBudgetsListUpperBanner(new OnAdViewLoadListener() {
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
    BudgetsListFragment.this.budgetsListPresenter.loadData();
  }

  @Override protected void onScrolled(RecyclerView recyclerView, int dx, int dy,
      ListLayoutManager layoutManager) {
    BudgetsListFragment.this.budgetsListPresenter.listScrolled(layoutManager);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.budgetsListPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.budgetsListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.budgetsListPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.budgetsListPresenter =
        new BudgetsListPresenter(this);
  }

  @Override public void budgetsLoaded(List<BudgetModel> budgetModels,
      boolean isInitialLoad) {
    if (budgetModels != null) {
      if (this.budgetsAdapter == null) {
        this.budgetsAdapter = new BudgetsAdapter(getActivity(), budgetModels);
        setAdapter(budgetsAdapter);
      } else {
        if (isInitialLoad) {
          this.budgetsAdapter.setBudgetsCollection(budgetModels);
        } else {
          this.budgetsAdapter.addBudgetsCollection(budgetModels);
        }
      }
    }
  }
}
