package com.novachevskyi.expenseslite.domain.repository.datasource.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.RepositoryCallback;

public interface BudgetsDataStore {

  interface BudgetDataCallback extends RepositoryCallback {
    void onBudgetLoaded(BudgetEntity budgetEntity);

    void onError(RepositoryErrorBundle error);
  }

  interface BudgetsDataCallback extends RepositoryCallback {
    void onBudgetsLoaded(BudgetsEntity budgetsEntity);

    void onError(RepositoryErrorBundle error);
  }

  void getList(int limit, int skip, BudgetsDataCallback budgetsDataCallback);

  void create(BudgetEntity budgetEntity, BudgetDataCallback budgetsDataCallback);
}
