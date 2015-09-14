package com.novachevskyi.expenseslite.presentation.view.base.budgets;

import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface BudgetLoadView extends LoadDataView {
  void budgetLoaded(BudgetModel budget);

  void amountDataError(String message);

  void amountDataAccepted();
}
