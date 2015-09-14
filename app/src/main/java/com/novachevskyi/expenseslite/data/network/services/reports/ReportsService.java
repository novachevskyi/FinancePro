package com.novachevskyi.expenseslite.data.network.services.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoriesMonthReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.GeneralReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.MonthReportWhereEntity;
import com.novachevskyi.expenseslite.data.models.reports.ReportWhereEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportHolderEntity;
import com.novachevskyi.expenseslite.data.models.reports.YearReportWhereEntity;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ReportsService {
  @POST("/1/functions/getGeneralReport")
  public GeneralReportHolderEntity getGeneralReport(@Body ReportWhereEntity report);

  @POST("/1/functions/getYearReport")
  public YearReportHolderEntity getYearReport(@Body YearReportWhereEntity report);

  @POST("/1/functions/getCategoriesMonthReport")
  public CategoriesMonthReportHolderEntity getCategoriesMonthReport(@Body MonthReportWhereEntity report);
}
