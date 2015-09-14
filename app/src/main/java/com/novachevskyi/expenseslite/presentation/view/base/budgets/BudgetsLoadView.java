package com.novachevskyi.expenseslite.presentation.view.base.budgets;

import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadListDataView;
import java.util.List;

public interface BudgetsLoadView extends LoadListDataView {
  void budgetsLoaded(List<BudgetModel> budgetModels, boolean isInitialLoad);
}
