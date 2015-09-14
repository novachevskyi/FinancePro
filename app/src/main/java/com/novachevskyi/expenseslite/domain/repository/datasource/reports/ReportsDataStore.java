package com.novachevskyi.expenseslite.domain.repository.datasource.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.RepositoryCallback;

public interface ReportsDataStore {

  interface ReportGeneralDataCallback extends RepositoryCallback {
    void onReportGeneralLoaded(GeneralReportHolderEntity reportHolderEntity);

    void onError(RepositoryErrorBundle error);
  }

  interface ReportYearDataCallback extends RepositoryCallback {
    void onReportYearLoaded(YearReportHolderEntity reportHolderEntity);

    void onError(RepositoryErrorBundle error);
  }

  interface ReportCategoriesMonthDataCallback extends RepositoryCallback {
    void onReportCategoriesMonthLoaded(CategoriesMonthReportHolderEntity reportHolderEntity);

    void onError(RepositoryErrorBundle error);
  }

  void getCategoriesMonthReport(ReportCategoriesMonthDataCallback reportCategoriesMonthDataCallback);

  void getYearReport(ReportYearDataCallback reportYearDataCallback, int year);

  void getGeneralReport(ReportGeneralDataCallback reportGeneralDataCallback);
}
