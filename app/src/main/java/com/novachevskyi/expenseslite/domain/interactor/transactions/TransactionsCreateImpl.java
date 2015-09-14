package com.novachevskyi.expenseslite.domain.interactor.transactions;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.TransactionsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.transactions.TransactionsDataStore;

public class TransactionsCreateImpl extends InteractorBase {

  private final TransactionsDataRepository transactionsDataRepository;

  TransactionEntity transactionEntity;

  public TransactionsCreateImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    transactionsDataRepository = TransactionsDataRepository.getInstance();
  }

  public void execute(TransactionsDataStore.TransactionDataCallback callback,
      TransactionEntity transactionEntity) {
    this.transactionEntity = transactionEntity;

    executeBase(callback);
  }

  @Override public void run() {
    transactionsDataRepository.create(transactionEntity, repositoryCallback);
  }

  protected final TransactionsDataStore.TransactionDataCallback repositoryCallback =
      new TransactionsDataStore.TransactionDataCallback() {
        @Override public void onTransactionLoaded(TransactionEntity transactionEntity) {
          notifyGetListSuccess(transactionEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final TransactionEntity transactionEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((TransactionsDataStore.TransactionDataCallback) callback).onTransactionLoaded(
            transactionEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((TransactionsDataStore.TransactionDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
