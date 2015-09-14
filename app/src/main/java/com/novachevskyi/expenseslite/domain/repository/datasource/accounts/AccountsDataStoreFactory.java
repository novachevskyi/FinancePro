package com.novachevskyi.expenseslite.domain.repository.datasource.accounts;

public class AccountsDataStoreFactory {
  public AccountsDataStore create() {
    AccountsDataStore accountsDataStore;

    accountsDataStore = createCloudDataStore();

    return accountsDataStore;
  }

  private AccountsDataStore createCloudDataStore() {
    return new CloudAccountsDataStore();
  }
}
