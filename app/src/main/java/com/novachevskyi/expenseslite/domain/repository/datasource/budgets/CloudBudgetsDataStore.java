package com.novachevskyi.expenseslite.domain.repository.datasource.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetWhereEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import com.novachevskyi.expenseslite.data.network.RestApi;
import com.novachevskyi.expenseslite.data.network.helpers.CurrentUserId;
import com.novachevskyi.expenseslite.data.network.services.budgets.BudgetsServiceImpl;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import org.joda.time.LocalDate;
import retrofit.RetrofitError;

public class CloudBudgetsDataStore implements BudgetsDataStore {

  @Override public void getList(int limit, int skip,
      final BudgetsDataCallback budgetsDataCallback) {
    BudgetWhereEntity budgetWhereEntity = new BudgetWhereEntity();
    budgetWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    budgetWhereEntity.currentDate = new LocalDate().toString();
    budgetWhereEntity.limit = limit;
    budgetWhereEntity.skip = skip;

    BudgetsServiceImpl budgetsService = RestApi.getInstance().getBudgetsService();
    budgetsService.getList(budgetWhereEntity, new BudgetsServiceImpl.BudgetsCallback() {
      @Override public void onBudgetEntitiesLoaded(BudgetsEntity budgetsEntity) {
        budgetsDataCallback.onBudgetsLoaded(budgetsEntity);
      }

      @Override public void onError(RetrofitError error) {
        budgetsDataCallback.onError(new RepositoryErrorBundle(error));
      }
    });
  }

  @Override public void create(BudgetEntity budgetEntity,
      final BudgetDataCallback budgetDataCallback) {
    if (budgetEntity != null) {
      budgetEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    }

    BudgetsServiceImpl budgetsService = RestApi.getInstance().getBudgetsService();
    budgetsService.create(budgetEntity, new BudgetsServiceImpl.BudgetCallback() {
      @Override public void onBudgetEntityLoaded(BudgetEntity budgetResultEntity) {
        budgetDataCallback.onBudgetLoaded(budgetResultEntity);
      }

      @Override public void onError(RetrofitError error) {
        budgetDataCallback.onError(new RepositoryErrorBundle(error));
      }
    });
  }
}
