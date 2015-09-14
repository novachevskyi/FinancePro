package com.novachevskyi.expenseslite.presentation.model.reports;

import com.novachevskyi.expenseslite.presentation.model.transactions.Category;

public class CategoryReportModel {
  private Category category;
  private double amount;

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
