package com.novachevskyi.expenseslite.domain.repository.datasource.budgets;

public class BudgetsDataStoreFactory {
  public BudgetsDataStore create() {
    BudgetsDataStore budgetsDataStore;

    budgetsDataStore = createCloudDataStore();

    return budgetsDataStore;
  }

  private BudgetsDataStore createCloudDataStore() {
    return new CloudBudgetsDataStore();
  }
}
