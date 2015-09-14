package com.novachevskyi.expenseslite.domain.interactor.reports;

import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.ReportsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;

public class ReportsYearImpl extends InteractorBase {

  private final ReportsDataRepository reportsDataRepository;

  private int year;

  public ReportsYearImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    reportsDataRepository = ReportsDataRepository.getInstance();
  }

  public void execute(ReportsDataStore.ReportYearDataCallback callback, int year) {
    this.year = year;

    executeBase(callback);
  }

  @Override public void run() {
    reportsDataRepository.getYearReport(repositoryCallback, this.year);
  }

  protected final ReportsDataStore.ReportYearDataCallback repositoryCallback =
      new ReportsDataStore.ReportYearDataCallback() {
        @Override public void onReportYearLoaded(YearReportHolderEntity reportHolderEntity) {
          notifyGetListSuccess(reportHolderEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final YearReportHolderEntity reportHolderEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((ReportsDataStore.ReportYearDataCallback) callback).onReportYearLoaded(
            reportHolderEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((ReportsDataStore.ReportYearDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
