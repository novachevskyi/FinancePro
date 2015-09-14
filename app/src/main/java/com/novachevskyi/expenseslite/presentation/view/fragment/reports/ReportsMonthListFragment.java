package com.novachevskyi.expenseslite.presentation.view.fragment.reports;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdView;
import com.novachevskyi.expenseslite.presentation.model.reports.MonthReportModel;
import com.novachevskyi.expenseslite.presentation.presenter.reports.ReportsMonthListPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.adapter.reports.ReportsMonthAdapter;
import com.novachevskyi.expenseslite.presentation.view.base.reports.ReportsMonthLoadView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseListFragment;
import java.util.List;
import org.joda.time.LocalDate;

public class ReportsMonthListFragment extends BaseListFragment implements ReportsMonthLoadView {

  private ReportsMonthListPresenter reportsMonthListPresenter;

  private ReportsMonthAdapter reportsMonthAdapter;

  private ViewGroup topContainer;

  public static ReportsMonthListFragment newInstance() {
    ReportsMonthListFragment reportsMonthListFragment = new ReportsMonthListFragment();

    Bundle argumentsBundle = new Bundle();
    reportsMonthListFragment.setArguments(argumentsBundle);

    return reportsMonthListFragment;
  }

  @Override protected String getEmptyText() {
    return null;
  }

  @Override protected void initializeViewStubHeader(ViewGroup container) {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      topContainer = container;

      constructCalendarListUpperBanner(new OnAdViewLoadListener() {
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
    ReportsMonthListFragment.this.reportsMonthListPresenter.loadData();
  }

  @Override protected void onScrolled(RecyclerView recyclerView, int dx, int dy,
      ListLayoutManager layoutManager) {
    ReportsMonthListFragment.this.reportsMonthListPresenter.listScrolled(layoutManager);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.reportsMonthListPresenter.initialize(new LocalDate().getYear());
  }

  @Override public void onResume() {
    super.onResume();
    this.reportsMonthListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.reportsMonthListPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.reportsMonthListPresenter =
        new ReportsMonthListPresenter(this);
  }

  @Override public void reportsLoaded(List<MonthReportModel> reportModels,
      boolean isInitialLoad) {
    if (reportModels != null) {
      if (this.reportsMonthAdapter == null) {
        this.reportsMonthAdapter = new ReportsMonthAdapter(getActivity(), reportModels);
        setAdapter(reportsMonthAdapter);
      } else {
        if (isInitialLoad) {
          this.reportsMonthAdapter.setReportsCollection(reportModels);
        } else {
          this.reportsMonthAdapter.addReportsCollection(reportModels);
        }
      }

      this.reportsMonthAdapter.setOnItemClickListener(
          new ReportsMonthAdapter.OnItemClickListener() {
            @Override public void onReportItemClicked(MonthReportModel reportModel) {
              getNavigator().navigateToTransactionsByMonthListScreen(getActivity(),
                  reportModel.getMonth());
            }
          });
    }
  }
}
