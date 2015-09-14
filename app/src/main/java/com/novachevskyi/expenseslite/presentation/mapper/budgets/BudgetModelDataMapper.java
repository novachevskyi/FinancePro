package com.novachevskyi.expenseslite.presentation.mapper.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.joda.time.LocalDate;

public class BudgetModelDataMapper {

  public BudgetModel transform(BudgetEntity budgetEntity) {
    BudgetModel budgetModel = new BudgetModel();

    budgetModel.setCategory(Category.getCategory(budgetEntity.category));
    budgetModel.setUserId(budgetEntity.userId);
    if (budgetEntity.alertAmount != null) {
      budgetModel.setAlertAmount(budgetEntity.alertAmount);
    }
    if (budgetEntity.amount != null) {
      budgetModel.setAmount(budgetEntity.amount);
    }
    if (budgetEntity.startAmount != null) {
      budgetModel.setStartAmount(budgetEntity.startAmount);
    }
    budgetModel.setBudgetId(budgetEntity.objectId);
    if (budgetEntity.dateFrom != null) {
      budgetModel.setDateFrom(new LocalDate(budgetEntity.dateFrom));
    }
    if (budgetEntity.dateTo != null) {
      budgetModel.setDateTo(new LocalDate(budgetEntity.dateTo));
    }
    budgetModel.setExpired(budgetEntity.isExpired);
    budgetModel.setNotes(budgetEntity.notes);

    return budgetModel;
  }

  private Collection<BudgetModel> transform(
      Collection<BudgetEntity> budgetsCollection) {
    Collection<BudgetModel> budgetModelCollection;

    if (budgetsCollection != null && !budgetsCollection.isEmpty()) {
      budgetModelCollection = new ArrayList<>();
      for (BudgetEntity budgetEntity : budgetsCollection) {
        budgetModelCollection.add(transform(budgetEntity));
      }
    } else {
      budgetModelCollection = Collections.emptyList();
    }

    return budgetModelCollection;
  }

  public Collection<BudgetModel> transform(BudgetsEntity budgetsEntity) {
    Collection<BudgetModel> budgetModelCollection = null;

    if (budgetsEntity != null) {
      BudgetEntity[] budgetEntities = budgetsEntity.result;

      if (budgetEntities != null) {
        budgetModelCollection = transform(Arrays.asList(budgetEntities));
      }
    }

    if (budgetModelCollection == null) {
      budgetModelCollection = Collections.emptyList();
    }

    return budgetModelCollection;
  }
}
