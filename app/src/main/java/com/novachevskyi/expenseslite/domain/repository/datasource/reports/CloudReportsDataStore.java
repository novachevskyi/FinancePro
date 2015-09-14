package com.novachevskyi.expenseslite.domain.repository.datasource.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.MonthReportWhereEntity;
import com.novachevskyi.expenseslite.data.models.reports.ReportWhereEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportWhereEntity;
import com.novachevskyi.expenseslite.data.network.RestApi;
import com.novachevskyi.expenseslite.data.network.helpers.CurrentUserId;
import com.novachevskyi.expenseslite.data.network.services.reports.ReportsServiceImpl;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import retrofit.RetrofitError;

public class CloudReportsDataStore implements ReportsDataStore {

  @Override public void getGeneralReport(
      final ReportGeneralDataCallback reportGeneralDataCallback) {
    ReportWhereEntity reportWhereEntity = new ReportWhereEntity();
    reportWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    reportWhereEntity.currentDate = new LocalDate().toString();

    ReportsServiceImpl reportsService = RestApi.getInstance().getReportsService();
    reportsService.getReportGeneral(reportWhereEntity,
        new ReportsServiceImpl.ReportGeneralCallback() {
          @Override
          public void onReportGeneralLoaded(GeneralReportHolderEntity reportHolderEntity) {
            reportGeneralDataCallback.onReportGeneralLoaded(reportHolderEntity);
          }

          @Override public void onError(RetrofitError error) {
            reportGeneralDataCallback.onError(new RepositoryErrorBundle(error));
          }
        });
  }

  @Override public void getCategoriesMonthReport(
      final ReportCategoriesMonthDataCallback reportCategoriesMonthDataCallback) {
    MonthReportWhereEntity reportWhereEntity = new MonthReportWhereEntity();
    reportWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
    reportWhereEntity.yearAndMonth = formatter.print(new LocalDate());

    ReportsServiceImpl reportsService = RestApi.getInstance().getReportsService();
    reportsService.getCategoriesMonthReport(reportWhereEntity,
        new ReportsServiceImpl.ReportCategoriesMonthCallback() {
          @Override public void onReportCategoriesMonthLoaded(
              CategoriesMonthReportHolderEntity reportHolderEntity) {
            reportCategoriesMonthDataCallback.onReportCategoriesMonthLoaded(reportHolderEntity);
          }

          @Override public void onError(RetrofitError error) {
            reportCategoriesMonthDataCallback.onError(new RepositoryErrorBundle(error));
          }
        });
  }

  @Override public void getYearReport(
      final ReportYearDataCallback reportYearDataCallback, int year) {
    YearReportWhereEntity reportWhereEntity = new YearReportWhereEntity();
    reportWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    reportWhereEntity.year = String.valueOf(year);

    ReportsServiceImpl reportsService = RestApi.getInstance().getReportsService();
    reportsService.getReportYear(reportWhereEntity,
        new ReportsServiceImpl.ReportYearCallback() {
          @Override
          public void onReportYearLoaded(YearReportHolderEntity reportHolderEntity) {
            reportYearDataCallback.onReportYearLoaded(reportHolderEntity);
          }

          @Override public void onError(RetrofitError error) {
            reportYearDataCallback.onError(new RepositoryErrorBundle(error));
          }
        });
  }
}
