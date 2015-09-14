package com.novachevskyi.expenseslite.presentation.model.reports;

import java.util.List;

public class ReportWithCategoriesModel extends ReportBaseModel {
  private List<CategoryReportModel> incomeByCategories;
  private List<CategoryReportModel> spentByCategories;

  public ReportWithCategoriesModel() {

  }

  public ReportWithCategoriesModel(ReportBaseModel baseModel) {
    super(baseModel);
  }

  public List<CategoryReportModel> getIncomeByCategories() {
    return incomeByCategories;
  }

  public void setIncomeByCategories(List<CategoryReportModel> incomeByCategories) {
    this.incomeByCategories = incomeByCategories;
  }

  public List<CategoryReportModel> getSpentByCategories() {
    return spentByCategories;
  }

  public void setSpentByCategories(List<CategoryReportModel> spentByCategories) {
    this.spentByCategories = spentByCategories;
  }
}
