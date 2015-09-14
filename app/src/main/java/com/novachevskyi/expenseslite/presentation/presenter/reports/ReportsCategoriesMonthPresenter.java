package com.novachevskyi.expenseslite.presentation.presenter.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.reports.ReportsCategoriesMonthImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.reports.ReportWithCategoriesModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportWithCategoriesModel;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.view.base.reports.ReportsCategoriesMonthLoadView;

public class ReportsCategoriesMonthPresenter implements Presenter {

  private final ReportsCategoriesMonthLoadView reportsCategoriesMonthLoadView;

  private final ReportWithCategoriesModelDataMapper reportWithCategoriesModelDataMapper;

  private final ReportsCategoriesMonthImpl reportsCategoriesMonth;

  private boolean isNeedToUpdate;

  public ReportsCategoriesMonthPresenter(
      ReportsCategoriesMonthLoadView reportsCategoriesMonthLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.reportsCategoriesMonth = new ReportsCategoriesMonthImpl(postExecutionThread);

    this.reportWithCategoriesModelDataMapper = new ReportWithCategoriesModelDataMapper();

    this.reportsCategoriesMonthLoadView = reportsCategoriesMonthLoadView;
  }

  @Override public void resume() {
    if(isNeedToUpdate) {
      initialize();
      isNeedToUpdate = false;
    }
  }

  @Override public void pause() {
    isNeedToUpdate = true;
  }

  private void showViewLoading() {
    this.reportsCategoriesMonthLoadView.showLoading();
  }

  private void hideViewLoading() {
    this.reportsCategoriesMonthLoadView.hideLoading();
  }

  private void showRetry() {
    this.reportsCategoriesMonthLoadView.showRetry();
  }

  private void hideRetry() {
    this.reportsCategoriesMonthLoadView.hideRetry();
  }

  private void showMainView() {
    this.reportsCategoriesMonthLoadView.showMainView();
  }

  private void hideMainView() {
    this.reportsCategoriesMonthLoadView.hideMainView();
  }

  public void initialize() {
    showViewLoading();
    hideRetry();
    hideMainView();
    this.reportsCategoriesMonth.execute(reportGeneralDataCallback);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage =
        ErrorMessageFactory.create(this.reportsCategoriesMonthLoadView.getContext(),
            errorBundle.getException());
    this.reportsCategoriesMonthLoadView.showError(errorMessage);
  }

  private final ReportsDataStore.ReportCategoriesMonthDataCallback reportGeneralDataCallback =
      new ReportsDataStore.ReportCategoriesMonthDataCallback() {
        @Override public void onReportCategoriesMonthLoaded(
            CategoriesMonthReportHolderEntity reportHolderEntity) {
          ReportsCategoriesMonthPresenter.this.hideViewLoading();
          ReportsCategoriesMonthPresenter.this.showMainView();
          ReportsCategoriesMonthPresenter.this.reportsCategorieMonthLoaded(reportHolderEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          ReportsCategoriesMonthPresenter.this.hideViewLoading();
          ReportsCategoriesMonthPresenter.this.showRetry();
          ReportsCategoriesMonthPresenter.this.hideMainView();
          ReportsCategoriesMonthPresenter.this.showErrorMessage(error);
        }
      };

  private void reportsCategorieMonthLoaded(CategoriesMonthReportHolderEntity reportHolderEntity) {
    ReportWithCategoriesModel reportWithCategoriesModel =
        reportWithCategoriesModelDataMapper.transform(reportHolderEntity.result);
    this.reportsCategoriesMonthLoadView.categoriesMonthReportLoaded(reportWithCategoriesModel);
  }
}
