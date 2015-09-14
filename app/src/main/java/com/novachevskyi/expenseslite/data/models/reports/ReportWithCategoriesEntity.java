package com.novachevskyi.expenseslite.data.models.reports;

public class ReportWithCategoriesEntity extends ReportBaseEntity {
  public CategoryReportEntity[] incomeByCategories;
  public CategoryReportEntity[] spentByCategories;
}
