package com.novachevskyi.expenseslite.domain.interactor.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.BudgetsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.budgets.BudgetsDataStore;

public class BudgetsGetListImpl extends InteractorBase {

  private int limit;
  private int skip;

  private final BudgetsDataRepository budgetsDataRepository;

  public BudgetsGetListImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    budgetsDataRepository = BudgetsDataRepository.getInstance();
  }

  public void execute(BudgetsDataStore.BudgetsDataCallback callback, int limit, int skip) {
    this.limit = limit;
    this.skip = skip;

    executeBase(callback);
  }

  @Override public void run() {
    budgetsDataRepository.getList(limit, skip, repositoryCallback);
  }

  protected final BudgetsDataStore.BudgetsDataCallback repositoryCallback =
      new BudgetsDataStore.BudgetsDataCallback() {
        @Override public void onBudgetsLoaded(BudgetsEntity budgetsEntity) {
          notifyGetListSuccess(budgetsEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final BudgetsEntity budgetsEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((BudgetsDataStore.BudgetsDataCallback) callback).onBudgetsLoaded(budgetsEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((BudgetsDataStore.BudgetsDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
