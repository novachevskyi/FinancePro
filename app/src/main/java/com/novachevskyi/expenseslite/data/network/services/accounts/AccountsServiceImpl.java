package com.novachevskyi.expenseslite.data.network.services.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountWhereEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import java.util.Map;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class AccountsServiceImpl {

  public interface AccountCallback {
    void onAccountEntityLoaded(AccountEntity accountEntity);

    void onError(RetrofitError error);
  }

  public interface AccountsTotalAmountCallback {
    void onAccountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntity);

    void onError(RetrofitError error);
  }

  public interface AccountsCallback {
    void onAccountEntitiesLoaded(AccountsEntity accountEntities);

    void onError(RetrofitError error);
  }

  private AccountsService accountsService;

  public AccountsServiceImpl(RestAdapter restAdapter) {
    accountsService = restAdapter.create(AccountsService.class);
  }

  public void getList(AccountsCallback accountsCallback, int limit, int skip,
      Map<String, String> where) {
    try {
      AccountsEntity accountEntities = this.accountsService.getList(limit, skip, where);
      accountsCallback.onAccountEntitiesLoaded(accountEntities);
    } catch (RetrofitError error) {
      accountsCallback.onError(error);
    }
  }

  public void getTotalAmount(AccountWhereEntity accountWhereEntity,
      AccountsTotalAmountCallback accountCallback) {
    try {
      AccountsTotalAmountEntity accountEntityResult =
          this.accountsService.getTotalAmount(accountWhereEntity);
      accountCallback.onAccountsTotalAmountLoaded(accountEntityResult);
    } catch (RetrofitError error) {
      accountCallback.onError(error);
    }
  }

  public void create(AccountEntity accountEntity, AccountCallback accountCallback) {
    try {
      AccountEntity accountEntityResult = this.accountsService.create(accountEntity);
      accountCallback.onAccountEntityLoaded(accountEntityResult);
    } catch (RetrofitError error) {
      accountCallback.onError(error);
    }
  }
}
