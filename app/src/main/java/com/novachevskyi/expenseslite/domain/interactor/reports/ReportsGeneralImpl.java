package com.novachevskyi.expenseslite.domain.interactor.reports;

import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.ReportsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;

public class ReportsGeneralImpl extends InteractorBase {

  private final ReportsDataRepository reportsDataRepository;

  public ReportsGeneralImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    reportsDataRepository = ReportsDataRepository.getInstance();
  }

  public void execute(ReportsDataStore.ReportGeneralDataCallback callback) {
    executeBase(callback);
  }

  @Override public void run() {
    reportsDataRepository.getGeneralReport(repositoryCallback);
  }

  protected final ReportsDataStore.ReportGeneralDataCallback repositoryCallback =
      new ReportsDataStore.ReportGeneralDataCallback() {
        @Override public void onReportGeneralLoaded(GeneralReportHolderEntity reportHolderEntity) {
          notifyGetListSuccess(reportHolderEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final GeneralReportHolderEntity reportHolderEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((ReportsDataStore.ReportGeneralDataCallback) callback).onReportGeneralLoaded(
            reportHolderEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((ReportsDataStore.ReportGeneralDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
