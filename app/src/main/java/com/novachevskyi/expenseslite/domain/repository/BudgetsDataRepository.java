package com.novachevskyi.expenseslite.domain.repository;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.budgets.BudgetsDataStore;
import com.novachevskyi.expenseslite.domain.repository.datasource.budgets.BudgetsDataStoreFactory;

public class BudgetsDataRepository implements BudgetsDataStore {

  private static BudgetsDataRepository instance;

  public static BudgetsDataRepository getInstance() {
    if (instance == null) {
      synchronized (BudgetsDataRepository.class) {
        if (instance == null) {
          instance = new BudgetsDataRepository();
        }
      }
    }
    return instance;
  }

  private final BudgetsDataStoreFactory budgetsDataStoreFactory;

  protected BudgetsDataRepository() {
    this.budgetsDataStoreFactory = new BudgetsDataStoreFactory();
  }

  @Override public void getList(int limit, int skip,
      final BudgetsDataCallback budgetsDataCallback) {
    final BudgetsDataStore budgetsDataStore = this.budgetsDataStoreFactory.create();
    budgetsDataStore.getList(limit, skip, new BudgetsDataCallback() {
      @Override public void onBudgetsLoaded(BudgetsEntity budgetsEntity) {
        budgetsDataCallback.onBudgetsLoaded(budgetsEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        budgetsDataCallback.onError(error);
      }
    });
  }

  @Override public void create(BudgetEntity budgetEntity,
      final BudgetDataCallback budgetDataCallback) {
    final BudgetsDataStore budgetsDataStore = this.budgetsDataStoreFactory.create();
    budgetsDataStore.create(budgetEntity, new BudgetDataCallback() {
      @Override public void onBudgetLoaded(BudgetEntity budgetEntity) {
        budgetDataCallback.onBudgetLoaded(budgetEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        budgetDataCallback.onError(error);
      }
    });
  }
}
