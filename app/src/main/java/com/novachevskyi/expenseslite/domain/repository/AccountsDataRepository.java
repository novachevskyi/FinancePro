package com.novachevskyi.expenseslite.domain.repository;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStoreFactory;

public class AccountsDataRepository implements AccountsDataStore {

  private static AccountsDataRepository instance;

  public static AccountsDataRepository getInstance() {
    if (instance == null) {
      synchronized (AccountsDataRepository.class) {
        if (instance == null) {
          instance = new AccountsDataRepository();
        }
      }
    }
    return instance;
  }

  private final AccountsDataStoreFactory accountDataStoreFactory;

  protected AccountsDataRepository() {
    this.accountDataStoreFactory = new AccountsDataStoreFactory();
  }

  @Override public void getList(int limit, int skip,
      final AccountsDataCallback accountsDataCallback) {
    final AccountsDataStore accountsDataStore = this.accountDataStoreFactory.create();
    accountsDataStore.getList(limit, skip, new AccountsDataCallback() {
      @Override public void onAccountsLoaded(AccountsEntity accountEntities) {
        accountsDataCallback.onAccountsLoaded(accountEntities);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        accountsDataCallback.onError(error);
      }
    });
  }

  @Override public void getTotalAmount(
      final AccountsTotalAmountCallback accountsDataCallback) {
    final AccountsDataStore accountsDataStore = this.accountDataStoreFactory.create();
    accountsDataStore.getTotalAmount(new AccountsTotalAmountCallback() {
      @Override public void onAccountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities) {
        accountsDataCallback.onAccountsTotalAmountLoaded(accountEntities);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        accountsDataCallback.onError(error);
      }
    });
  }

  @Override public void create(AccountEntity accountEntity,
      final AccountDataCallback accountsDataCallback) {
    final AccountsDataStore accountsDataStore = this.accountDataStoreFactory.create();
    accountsDataStore.create(accountEntity, new AccountDataCallback() {
      @Override public void onAccountLoaded(AccountEntity accountEntity) {
        accountsDataCallback.onAccountLoaded(accountEntity);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        accountsDataCallback.onError(error);
      }
    });
  }
}
