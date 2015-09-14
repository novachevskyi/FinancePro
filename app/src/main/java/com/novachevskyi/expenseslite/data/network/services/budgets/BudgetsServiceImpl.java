package com.novachevskyi.expenseslite.data.network.services.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetWhereEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class BudgetsServiceImpl {

  public interface BudgetCallback {
    void onBudgetEntityLoaded(BudgetEntity budgetEntity);

    void onError(RetrofitError error);
  }

  public interface BudgetsCallback {
    void onBudgetEntitiesLoaded(BudgetsEntity budgetsEntity);

    void onError(RetrofitError error);
  }

  private BudgetsService budgetsService;

  public BudgetsServiceImpl(RestAdapter restAdapter) {
    budgetsService = restAdapter.create(BudgetsService.class);
  }

  public void getList(BudgetWhereEntity budgetWhereEntity,
      BudgetsCallback budgetsCallback) {
    try {
      BudgetsEntity budgetsEntity =
          this.budgetsService.getList(budgetWhereEntity);
      budgetsCallback.onBudgetEntitiesLoaded(budgetsEntity);
    } catch (RetrofitError error) {
      budgetsCallback.onError(error);
    }
  }

  public void create(BudgetEntity budgetEntity, BudgetCallback budgetCallback) {
    try {
      BudgetEntity budgetEntityResult = this.budgetsService.create(budgetEntity);
      budgetCallback.onBudgetEntityLoaded(budgetEntityResult);
    } catch (RetrofitError error) {
      budgetCallback.onError(error);
    }
  }
}
