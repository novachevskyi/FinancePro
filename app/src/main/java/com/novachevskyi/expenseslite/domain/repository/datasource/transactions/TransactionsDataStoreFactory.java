package com.novachevskyi.expenseslite.domain.repository.datasource.transactions;

public class TransactionsDataStoreFactory {
  public TransactionsDataStore create() {
    TransactionsDataStore transactionsDataStore;

    transactionsDataStore = createCloudDataStore();

    return transactionsDataStore;
  }

  private TransactionsDataStore createCloudDataStore() {
    return new CloudTransactionsDataStore();
  }
}
