package com.novachevskyi.expenseslite.domain.interactor.transactions;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.TransactionsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.transactions.TransactionsDataStore;
import org.joda.time.LocalDate;

public class TransactionsGetListImpl extends InteractorBase {

  private int limit;
  private int skip;

  private LocalDate dateFilter;

  private boolean isMonthFilter;

  private boolean isAccountFilter;
  private boolean isBudgetFilter;

  private String idFilter;

  private final TransactionsDataRepository transactionsDataRepository;

  public TransactionsGetListImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    transactionsDataRepository = TransactionsDataRepository.getInstance();
  }

  public void execute(TransactionsDataStore.TransactionsDataCallback callback, int limit,
      int skip) {
    this.limit = limit;
    this.skip = skip;

    executeBase(callback);
  }

  @Override public void run() {
    if (dateFilter != null) {
      if (isMonthFilter) {
        transactionsDataRepository.getListByMonth(limit, skip, repositoryCallback, dateFilter);
      } else {
        transactionsDataRepository.getList(limit, skip, repositoryCallback, dateFilter);
      }
    }
    else if(idFilter != null) {
      if(isAccountFilter) {
        transactionsDataRepository.getListByAccount(limit, skip, repositoryCallback, idFilter);
      }
    }
    else {
      transactionsDataRepository.getList(limit, skip, repositoryCallback);
    }
  }

  protected final TransactionsDataStore.TransactionsDataCallback repositoryCallback =
      new TransactionsDataStore.TransactionsDataCallback() {
        @Override public void onTransactionsLoaded(TransactionsEntity transactionsEntity) {
          notifyGetListSuccess(transactionsEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final TransactionsEntity transactionsEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((TransactionsDataStore.TransactionsDataCallback) callback).onTransactionsLoaded(
            transactionsEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((TransactionsDataStore.TransactionsDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }

  public void executeToday(TransactionsDataStore.TransactionsDataCallback callback,
      int limit, int skip) {
    this.dateFilter = new LocalDate();

    execute(callback, limit, skip);
  }

  public void executeThisMonth(TransactionsDataStore.TransactionsDataCallback callback,
      int limit, int skip) {
    this.dateFilter = new LocalDate();
    this.isMonthFilter = true;

    execute(callback, limit, skip);
  }

  public void executeMonth(TransactionsDataStore.TransactionsDataCallback callback,
      int limit, int skip, LocalDate dateFilter) {
    this.dateFilter = dateFilter;
    this.isMonthFilter = true;

    execute(callback, limit, skip);
  }

  public void executeAccount(TransactionsDataStore.TransactionsDataCallback callback,
      int limit, int skip, String idFilter) {
    this.idFilter = idFilter;
    this.isAccountFilter = true;

    execute(callback, limit, skip);
  }

  public void executeBudget(TransactionsDataStore.TransactionsDataCallback callback,
      int limit, int skip, String idFilter) {
    this.idFilter = idFilter;
    this.isBudgetFilter = true;

    execute(callback, limit, skip);
  }
}
