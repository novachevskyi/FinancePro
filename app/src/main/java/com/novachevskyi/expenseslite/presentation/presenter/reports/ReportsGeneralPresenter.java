package com.novachevskyi.expenseslite.presentation.presenter.reports;

import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.accounts.AccountsGetTotalAmountImpl;
import com.novachevskyi.expenseslite.domain.interactor.reports.ReportsGeneralImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.reports.GeneralReportModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.reports.GeneralReportModel;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.view.base.reports.ReportsGeneralLoadView;

public class ReportsGeneralPresenter implements Presenter {

  private final ReportsGeneralLoadView reportsGeneralLoadView;

  private final GeneralReportModelDataMapper generalReportModelDataMapper;

  private final ReportsGeneralImpl reportsGeneral;
  private final AccountsGetTotalAmountImpl accountsGetTotalAmount;

  private double currentTotalAmount;

  private boolean isNeedToUpdate;

  public ReportsGeneralPresenter(ReportsGeneralLoadView reportsGeneralLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.reportsGeneral = new ReportsGeneralImpl(postExecutionThread);
    this.accountsGetTotalAmount = new AccountsGetTotalAmountImpl(postExecutionThread);

    this.generalReportModelDataMapper = new GeneralReportModelDataMapper();

    this.reportsGeneralLoadView = reportsGeneralLoadView;
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
    this.reportsGeneralLoadView.showLoading();
  }

  private void hideViewLoading() {
    this.reportsGeneralLoadView.hideLoading();
  }

  private void showRetry() {
    this.reportsGeneralLoadView.showRetry();
  }

  private void hideRetry() {
    this.reportsGeneralLoadView.hideRetry();
  }

  private void showMainView() {
    this.reportsGeneralLoadView.showMainView();
  }

  private void hideMainView() {
    this.reportsGeneralLoadView.hideMainView();
  }

  public void initialize() {
    showViewLoading();
    hideRetry();
    hideMainView();

    this.accountsGetTotalAmount.execute(accountsTotalAmountCallback);
  }

  private final AccountsDataStore.AccountsTotalAmountCallback accountsTotalAmountCallback =
      new AccountsDataStore.AccountsTotalAmountCallback() {
        @Override
        public void onAccountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities) {
          ReportsGeneralPresenter.this.accountsTotalAmountLoaded(accountEntities);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          ReportsGeneralPresenter.this.hideViewLoading();
          ReportsGeneralPresenter.this.showRetry();
          ReportsGeneralPresenter.this.hideMainView();
          ReportsGeneralPresenter.this.showErrorMessage(error);
        }
      };

  private void accountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities) {
    if (accountEntities != null && accountEntities.result != null) {
      currentTotalAmount = accountEntities.result;
    }

    this.reportsGeneral.execute(reportGeneralDataCallback);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.reportsGeneralLoadView.getContext(),
        errorBundle.getException());
    this.reportsGeneralLoadView.showError(errorMessage);
  }

  private final ReportsDataStore.ReportGeneralDataCallback reportGeneralDataCallback =
      new ReportsDataStore.ReportGeneralDataCallback() {
        @Override public void onReportGeneralLoaded(GeneralReportHolderEntity reportHolderEntity) {
          ReportsGeneralPresenter.this.hideViewLoading();
          ReportsGeneralPresenter.this.showMainView();
          ReportsGeneralPresenter.this.reportsGeneralLoaded(reportHolderEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          ReportsGeneralPresenter.this.hideViewLoading();
          ReportsGeneralPresenter.this.showRetry();
          ReportsGeneralPresenter.this.hideMainView();
          ReportsGeneralPresenter.this.showErrorMessage(error);
        }
      };

  private void reportsGeneralLoaded(GeneralReportHolderEntity reportHolderEntity) {
    GeneralReportModel generalReportModel =
        generalReportModelDataMapper.transform(reportHolderEntity.result);
    generalReportModel.setCurrentTotalAmount(currentTotalAmount);
    this.reportsGeneralLoadView.generalReportLoaded(generalReportModel);
  }
}
