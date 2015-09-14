package com.novachevskyi.expenseslite.domain.repository;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStore;
import com.novachevskyi.expenseslite.domain.repository.datasource.reports.ReportsDataStoreFactory;

public class ReportsDataRepository implements ReportsDataStore {

  private static ReportsDataRepository instance;

  public static ReportsDataRepository getInstance() {
    if (instance == null) {
      synchronized (ReportsDataRepository.class) {
        if (instance == null) {
          instance = new ReportsDataRepository();
        }
      }
    }
    return instance;
  }

  private final ReportsDataStoreFactory reportsDataStoreFactory;

  protected ReportsDataRepository() {
    this.reportsDataStoreFactory = new ReportsDataStoreFactory();
  }

  @Override public void getCategoriesMonthReport(
      final ReportCategoriesMonthDataCallback reportCategoriesMonthDataCallback) {
    final ReportsDataStore reportsDataStore = this.reportsDataStoreFactory.create();
    reportsDataStore.getCategoriesMonthReport(new ReportCategoriesMonthDataCallback() {
      @Override public void onReportCategoriesMonthLoaded(
          CategoriesMonthReportHolderEntity reportHolderEntity) {
        reportCategoriesMonthDataCallback.onReportCategoriesMonthLoaded(reportHolderEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        reportCategoriesMonthDataCallback.onError(error);
      }
    });
  }

  @Override public void getYearReport(
      final ReportYearDataCallback reportYearDataCallback, int year) {
    final ReportsDataStore reportsDataStore = this.reportsDataStoreFactory.create();
    reportsDataStore.getYearReport(new ReportYearDataCallback() {
      @Override public void onReportYearLoaded(YearReportHolderEntity reportHolderEntity) {
        reportYearDataCallback.onReportYearLoaded(reportHolderEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        reportYearDataCallback.onError(error);
      }
    }, year);
  }

  @Override public void getGeneralReport(
      final ReportGeneralDataCallback reportGeneralDataCallback) {
    final ReportsDataStore reportsDataStore = this.reportsDataStoreFactory.create();
    reportsDataStore.getGeneralReport(new ReportGeneralDataCallback() {
      @Override public void onReportGeneralLoaded(GeneralReportHolderEntity reportHolderEntity) {
        reportGeneralDataCallback.onReportGeneralLoaded(reportHolderEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        reportGeneralDataCallback.onError(error);
      }
    });
  }
}
