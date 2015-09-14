package com.novachevskyi.expenseslite.presentation.model.reports;

public class MonthReportModel {
  private int month;
  private ReportBaseModel monthReport;

  public ReportBaseModel getMonthReport() {
    return monthReport;
  }

  public void setMonthReport(ReportBaseModel monthReport) {
    this.monthReport = monthReport;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }
}
