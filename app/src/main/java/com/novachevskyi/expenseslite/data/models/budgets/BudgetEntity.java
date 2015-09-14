package com.novachevskyi.expenseslite.data.models.budgets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BudgetEntity {
  public String objectId;
  public Integer category;
  public String dateFrom;
  public String dateTo;
  public Double amount;
  public Double startAmount;
  public Double alertAmount;
  public String notes;
  public String userId;
  public boolean isExpired;

  public BudgetEntity() {

  }

  public BudgetEntity(int category, String dateFrom, String dateTo,
      double amount, double alertAmount, String notes) {
    this.category = category;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.amount = amount;
    this.startAmount = amount;
    this.alertAmount = alertAmount;
    this.notes = notes;
  }
}
