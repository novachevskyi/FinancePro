package com.novachevskyi.expenseslite.domain.repository.datasource.transactions;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.RepositoryCallback;
import org.joda.time.LocalDate;

public interface TransactionsDataStore {

  interface TransactionDataCallback extends RepositoryCallback {
    void onTransactionLoaded(TransactionEntity transactionEntity);

    void onError(RepositoryErrorBundle error);
  }

  interface TransactionsDataCallback extends RepositoryCallback {
    void onTransactionsLoaded(TransactionsEntity transactionsEntity);

    void onError(RepositoryErrorBundle error);
  }

  void create(TransactionEntity transactionEntity, TransactionDataCallback transactionDataCallback);

  void getList(int limit, int skip, TransactionsDataCallback transactionsDataCallback);

  void getList(int limit, int skip, TransactionsDataCallback transactionsDataCallback,
      LocalDate date);

  void getListByMonth(int limit, int skip, TransactionsDataCallback transactionsDataCallback,
      LocalDate date);

  void getListByAccount(int limit, int skip, TransactionsDataCallback transactionsDataCallback,
      String filterId);
}
