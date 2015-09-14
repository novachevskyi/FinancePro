package com.novachevskyi.expenseslite.presentation.mapper.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class AccountModelDataMapper {

  public AccountModel transform(AccountEntity accountEntity) {
    AccountModel accountModel = new AccountModel();

    accountModel.setAccountId(accountEntity.objectId);
    accountModel.setAccountType(AccountType.getAccountType(accountEntity.paymentMethod));
    if (accountEntity.amount != null) {
      accountModel.setAmount(accountEntity.amount);
    }
    accountModel.setName(accountEntity.name);

    return accountModel;
  }

  private Collection<AccountModel> transform(Collection<AccountEntity> accountsCollection) {
    Collection<AccountModel> accountModelsCollection;

    if (accountsCollection != null && !accountsCollection.isEmpty()) {
      accountModelsCollection = new ArrayList<>();
      for (AccountEntity accountEntity : accountsCollection) {
        accountModelsCollection.add(transform(accountEntity));
      }
    } else {
      accountModelsCollection = Collections.emptyList();
    }

    return accountModelsCollection;
  }

  public Collection<AccountModel> transform(AccountsEntity accountsEntity) {
    Collection<AccountModel> accountModelsCollection = null;

    if (accountsEntity != null) {
      AccountEntity[] accountsEntities = accountsEntity.results;

      if (accountsEntities != null) {
        accountModelsCollection = transform(Arrays.asList(accountsEntities));
      }
    }

    if (accountModelsCollection == null) {
      accountModelsCollection = Collections.emptyList();
    }

    return accountModelsCollection;
  }
}
