package com.novachevskyi.expenseslite.domain.repository.datasource.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.RepositoryCallback;

public interface AccountsDataStore {

  interface AccountDataCallback extends RepositoryCallback {
    void onAccountLoaded(AccountEntity accountEntity);

    void onError(RepositoryErrorBundle error);
  }

  interface AccountsTotalAmountCallback extends RepositoryCallback {
    void onAccountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities);

    void onError(RepositoryErrorBundle error);
  }

  interface AccountsDataCallback extends RepositoryCallback {
    void onAccountsLoaded(AccountsEntity accountEntities);

    void onError(RepositoryErrorBundle error);
  }

  void getList(int limit, int skip, AccountsDataCallback accountsDataCallback);

  void getTotalAmount(AccountsTotalAmountCallback accountsDataCallback);

  void create(AccountEntity accountEntity, AccountDataCallback accountsDataCallback);
}
