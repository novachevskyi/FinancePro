package com.novachevskyi.expenseslite.domain.interactor.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.ReportsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;

public class ReportsCategoriesMonthImpl extends InteractorBase {

  private final ReportsDataRepository reportsDataRepository;

  public ReportsCategoriesMonthImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    reportsDataRepository = ReportsDataRepository.getInstance();
  }

  public void execute(ReportsDataStore.ReportCategoriesMonthDataCallback callback) {
    executeBase(callback);
  }

  @Override public void run() {
    reportsDataRepository.getCategoriesMonthReport(repositoryCallback);
  }

  protected final ReportsDataStore.ReportCategoriesMonthDataCallback repositoryCallback =
      new ReportsDataStore.ReportCategoriesMonthDataCallback() {
        @Override public void onReportCategoriesMonthLoaded(
            CategoriesMonthReportHolderEntity reportHolderEntity) {
          notifyGetListSuccess(reportHolderEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final CategoriesMonthReportHolderEntity reportHolderEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((ReportsDataStore.ReportCategoriesMonthDataCallback) callback).onReportCategoriesMonthLoaded(
            reportHolderEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((ReportsDataStore.ReportCategoriesMonthDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
