package com.novachevskyi.expenseslite.data.network.services.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetWhereEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import retrofit.http.Body;
import retrofit.http.POST;

public interface BudgetsService {
  @POST("/1/functions/getBudgets")
  public BudgetsEntity getList(@Body BudgetWhereEntity budget);

  @POST("/1/classes/Budget")
  public BudgetEntity create(@Body BudgetEntity budget);
}
