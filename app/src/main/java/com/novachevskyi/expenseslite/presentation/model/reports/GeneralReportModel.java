package com.novachevskyi.expenseslite.presentation.model.reports;

public class GeneralReportModel {
  private double currentTotalAmount;
  private double todaySpentAmount;
  private ReportBaseModel thisMonthReport;

  public double getCurrentTotalAmount() {
    return currentTotalAmount;
  }

  public void setCurrentTotalAmount(double currentTotalAmount) {
    this.currentTotalAmount = currentTotalAmount;
  }

  public double getTodaySpentAmount() {
    return todaySpentAmount;
  }

  public void setTodaySpentAmount(double todaySpentAmount) {
    this.todaySpentAmount = todaySpentAmount;
  }

  public ReportBaseModel getThisMonthReport() {
    return thisMonthReport;
  }

  public void setThisMonthReport(ReportBaseModel thisMonthReport) {
    this.thisMonthReport = thisMonthReport;
  }
}
