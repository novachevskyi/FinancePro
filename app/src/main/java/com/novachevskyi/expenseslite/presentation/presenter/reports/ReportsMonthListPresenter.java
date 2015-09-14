package com.novachevskyi.expenseslite.presentation.presenter.reports;

import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.reports.ReportsYearImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.reports.MonthReportModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.reports.MonthReportModel;
import com.novachevskyi.expenseslite.presentation.presenter.ListBasePresenter;
import com.novachevskyi.expenseslite.presentation.view.base.reports.ReportsMonthLoadView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReportsMonthListPresenter extends ListBasePresenter {

  private final ReportsMonthLoadView reportsMonthLoadView;

  private final MonthReportModelDataMapper reportModelDataMapper;

  private final ReportsYearImpl reportsYear;

  private int year;

  public ReportsMonthListPresenter(ReportsMonthLoadView reportsMonthLoadView) {
    super(reportsMonthLoadView);

    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.reportsYear = new ReportsYearImpl(postExecutionThread);

    this.reportModelDataMapper = new MonthReportModelDataMapper();

    this.reportsMonthLoadView = reportsMonthLoadView;
  }

  public void initialize(int year) {
    this.year = year;

    initializeListPresenter();
    setPagingDisabled(true);
    loadData();
  }

  public void loadData() {
    loadListFromScratch();
  }

  @Override
  protected void invokeLoadListMethods(int limit, int skip) {
    this.getReportsList();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.reportsMonthLoadView.getContext(),
        errorBundle.getException());
    this.reportsMonthLoadView.showError(errorMessage);

    loadingFailed(errorMessage);
  }

  private void reportsLoaded(YearReportHolderEntity reportHolderEntity) {
    Collection<MonthReportModel> collection =
        this.reportModelDataMapper.transform(reportHolderEntity.result);
    final List<MonthReportModel> monthReportModels =
        new ArrayList<>(collection);
    this.reportsMonthLoadView.reportsLoaded(monthReportModels, isNeedToUpdateView());

    loadingSucceed(monthReportModels.size());
  }

  private void getReportsList() {
    this.reportsYear.execute(this.reportsDataCallback, this.year);
  }

  private final ReportsDataStore.ReportYearDataCallback reportsDataCallback =
      new ReportsDataStore.ReportYearDataCallback() {
        @Override public void onReportYearLoaded(YearReportHolderEntity reportHolderEntity) {
          ReportsMonthListPresenter.this.reportsLoaded(reportHolderEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          ReportsMonthListPresenter.this.showErrorMessage(error);
        }
      };
}
