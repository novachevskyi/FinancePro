package com.novachevskyi.expenseslite.domain.interactor.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.BudgetsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.budgets.BudgetsDataStore;

public class BudgetsCreateImpl extends InteractorBase {

  private final BudgetsDataRepository budgetsDataRepository;

  BudgetEntity budgetEntity;

  public BudgetsCreateImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    budgetsDataRepository = BudgetsDataRepository.getInstance();
  }

  public void execute(BudgetsDataStore.BudgetDataCallback callback,
      BudgetEntity budgetEntity) {
    this.budgetEntity = budgetEntity;

    executeBase(callback);
  }

  @Override public void run() {
    budgetsDataRepository.create(budgetEntity, repositoryCallback);
  }

  protected final BudgetsDataStore.BudgetDataCallback repositoryCallback =
      new BudgetsDataStore.BudgetDataCallback() {
        @Override public void onBudgetLoaded(BudgetEntity budgetEntity) {
          notifyGetListSuccess(budgetEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final BudgetEntity budgetEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((BudgetsDataStore.BudgetDataCallback) callback).onBudgetLoaded(
            budgetEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((BudgetsDataStore.BudgetDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
