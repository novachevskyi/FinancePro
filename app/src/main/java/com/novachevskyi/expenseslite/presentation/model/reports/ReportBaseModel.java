package com.novachevskyi.expenseslite.presentation.model.reports;

public class ReportBaseModel {
  private double income;
  private double spent;

  public ReportBaseModel() {

  }

  public ReportBaseModel(ReportBaseModel baseModel) {
    this.income = baseModel.getIncome();
    this.spent = baseModel.getSpent();
  }

  public double getIncome() {
    return income;
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public double getSpent() {
    return spent;
  }

  public void setSpent(double spent) {
    this.spent = spent;
  }
}
