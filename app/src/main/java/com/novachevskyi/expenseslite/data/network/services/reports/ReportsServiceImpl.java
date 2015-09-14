package com.novachevskyi.expenseslite.data.network.services.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.MonthReportWhereEntity;
import com.novachevskyi.expenseslite.data.models.reports.ReportWhereEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportWhereEntity;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class ReportsServiceImpl {

  public interface ReportGeneralCallback {
    void onReportGeneralLoaded(GeneralReportHolderEntity reportHolderEntity);

    void onError(RetrofitError error);
  }

  public interface ReportYearCallback {
    void onReportYearLoaded(YearReportHolderEntity reportHolderEntity);

    void onError(RetrofitError error);
  }

  public interface ReportCategoriesMonthCallback {
    void onReportCategoriesMonthLoaded(CategoriesMonthReportHolderEntity reportHolderEntity);

    void onError(RetrofitError error);
  }

  private ReportsService reportsService;

  public ReportsServiceImpl(RestAdapter restAdapter) {
    reportsService = restAdapter.create(ReportsService.class);
  }

  public void getReportGeneral(ReportWhereEntity reportWhereEntity,
      ReportGeneralCallback reportsGeneralCallback) {
    try {
      GeneralReportHolderEntity reportHolderEntity =
          this.reportsService.getGeneralReport(reportWhereEntity);
      reportsGeneralCallback.onReportGeneralLoaded(reportHolderEntity);
    } catch (RetrofitError error) {
      reportsGeneralCallback.onError(error);
    }
  }

  public void getReportYear(YearReportWhereEntity reportWhereEntity,
      ReportYearCallback reportYearCallback) {
    try {
      YearReportHolderEntity reportHolderEntity =
          this.reportsService.getYearReport(reportWhereEntity);
      reportYearCallback.onReportYearLoaded(reportHolderEntity);
    } catch (RetrofitError error) {
      reportYearCallback.onError(error);
    }
  }

  public void getCategoriesMonthReport(MonthReportWhereEntity reportWhereEntity,
      ReportCategoriesMonthCallback reportYearCallback) {
    try {
      CategoriesMonthReportHolderEntity reportHolderEntity =
          this.reportsService.getCategoriesMonthReport(reportWhereEntity);
      reportYearCallback.onReportCategoriesMonthLoaded(reportHolderEntity);
    } catch (RetrofitError error) {
      reportYearCallback.onError(error);
    }
  }
}
