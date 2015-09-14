package com.novachevskyi.expenseslite.presentation.model.budgets;

import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import org.joda.time.LocalDate;

public class BudgetModel {

  private String budgetId;
  private Category category;
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private double amount;
  private double startAmount;
  private double alertAmount;
  private String notes;
  private String userId;
  private boolean isExpired;

  public String getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(String budgetId) {
    this.budgetId = budgetId;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
  }

  public LocalDate getDateTo() {
    return dateTo;
  }

  public void setDateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getAlertAmount() {
    return alertAmount;
  }

  public void setAlertAmount(double alertAmount) {
    this.alertAmount = alertAmount;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public boolean isExpired() {
    return isExpired;
  }

  public void setExpired(boolean isExpired) {
    this.isExpired = isExpired;
  }

  public double getStartAmount() {
    return startAmount;
  }

  public void setStartAmount(double startAmount) {
    this.startAmount = startAmount;
  }
}
