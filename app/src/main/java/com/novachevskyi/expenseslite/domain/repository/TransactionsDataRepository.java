package com.novachevskyi.expenseslite.domain.repository;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.transactions.TransactionsDataStore;
import com.novachevskyi.expenseslite.domain.repository.datasource.transactions.TransactionsDataStoreFactory;
import org.joda.time.LocalDate;

public class TransactionsDataRepository implements TransactionsDataStore {

  private static TransactionsDataRepository instance;

  public static TransactionsDataRepository getInstance() {
    if (instance == null) {
      synchronized (TransactionsDataRepository.class) {
        if (instance == null) {
          instance = new TransactionsDataRepository();
        }
      }
    }
    return instance;
  }

  private final TransactionsDataStoreFactory transactionsDataStoreFactory;

  protected TransactionsDataRepository() {
    this.transactionsDataStoreFactory = new TransactionsDataStoreFactory();
  }

  @Override public void create(TransactionEntity transactionEntity,
      final TransactionDataCallback transactionDataCallback) {
    final TransactionsDataStore transactionsDataStore = this.transactionsDataStoreFactory.create();
    transactionsDataStore.create(transactionEntity, new TransactionDataCallback() {
      @Override public void onTransactionLoaded(TransactionEntity transactionEntity) {
        transactionDataCallback.onTransactionLoaded(transactionEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        transactionDataCallback.onError(error);
      }
    });
  }

  @Override public void getList(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback) {
    final TransactionsDataStore transactionsDataStore = this.transactionsDataStoreFactory.create();
    transactionsDataStore.getList(limit, skip, new TransactionsDataCallback() {
      @Override public void onTransactionsLoaded(TransactionsEntity transactionsEntity) {
        transactionsDataCallback.onTransactionsLoaded(transactionsEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        transactionsDataCallback.onError(error);
      }
    });
  }

  @Override public void getList(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback, LocalDate localDate) {
    final TransactionsDataStore transactionsDataStore = this.transactionsDataStoreFactory.create();
    transactionsDataStore.getList(limit, skip, new TransactionsDataCallback() {
      @Override public void onTransactionsLoaded(TransactionsEntity transactionsEntity) {
        transactionsDataCallback.onTransactionsLoaded(transactionsEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        transactionsDataCallback.onError(error);
      }
    }, localDate);
  }

  @Override public void getListByMonth(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback, LocalDate date) {
    final TransactionsDataStore transactionsDataStore = this.transactionsDataStoreFactory.create();
    transactionsDataStore.getListByMonth(limit, skip, new TransactionsDataCallback() {
      @Override public void onTransactionsLoaded(TransactionsEntity transactionsEntity) {
        transactionsDataCallback.onTransactionsLoaded(transactionsEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        transactionsDataCallback.onError(error);
      }
    }, date);
  }

  @Override public void getListByAccount(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback, String idFilter) {
    final TransactionsDataStore transactionsDataStore = this.transactionsDataStoreFactory.create();
    transactionsDataStore.getListByAccount(limit, skip, new TransactionsDataCallback() {
      @Override public void onTransactionsLoaded(TransactionsEntity transactionsEntity) {
        transactionsDataCallback.onTransactionsLoaded(transactionsEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        transactionsDataCallback.onError(error);
      }
    }, idFilter);
  }
}
