package com.novachevskyi.expenseslite.domain.repository.datasource.accounts;

import com.novachevskyi.expenseslite.data.helpers.ObjectToJsonStringConverter;
import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountWhereEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import com.novachevskyi.expenseslite.data.network.ApiConstants;
import com.novachevskyi.expenseslite.data.network.RestApi;
import com.novachevskyi.expenseslite.data.network.helpers.CurrentUserId;
import com.novachevskyi.expenseslite.data.network.services.accounts.AccountsServiceImpl;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import java.util.HashMap;
import java.util.Map;
import retrofit.RetrofitError;

public class CloudAccountsDataStore implements AccountsDataStore {

  @Override public void getList(int limit, int skip,
      final AccountsDataCallback accountsDataCallback) {
    Map<String, String> where = new HashMap<>();

    AccountWhereEntity accountWhereEntity = new AccountWhereEntity();
    accountWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();

    where.put(ApiConstants.WHERE_CLAUSE, ObjectToJsonStringConverter.convert(accountWhereEntity));
    where.put(ApiConstants.ORDER_CLAUSE, ApiConstants.FIELD_NAME);

    AccountsServiceImpl accountsService = RestApi.getInstance().getAccountsService();
    accountsService.getList(new AccountsServiceImpl.AccountsCallback() {
      @Override public void onAccountEntitiesLoaded(AccountsEntity accountEntities) {
        accountsDataCallback.onAccountsLoaded(accountEntities);
      }

      @Override public void onError(RetrofitError error) {
        accountsDataCallback.onError(new RepositoryErrorBundle(error));
      }
    }, limit, skip, where);
  }

  @Override public void getTotalAmount(final AccountsTotalAmountCallback accountsDataCallback) {
    AccountWhereEntity accountWhereEntity = new AccountWhereEntity();
    accountWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();

    AccountsServiceImpl accountsService = RestApi.getInstance().getAccountsService();
    accountsService.getTotalAmount(accountWhereEntity,
        new AccountsServiceImpl.AccountsTotalAmountCallback() {
          @Override public void onAccountsTotalAmountLoaded(
              AccountsTotalAmountEntity accountEntity) {
            accountsDataCallback.onAccountsTotalAmountLoaded(accountEntity);
          }

          @Override public void onError(RetrofitError error) {
            accountsDataCallback.onError(new RepositoryErrorBundle(error));
          }
        });
  }

  @Override public void create(AccountEntity accountEntity,
      final AccountDataCallback accountDataCallback) {
    if (accountEntity != null) {
      accountEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    }

    AccountsServiceImpl accountsService = RestApi.getInstance().getAccountsService();
    accountsService.create(accountEntity, new AccountsServiceImpl.AccountCallback() {
      @Override public void onAccountEntityLoaded(AccountEntity accountEntity) {
        accountDataCallback.onAccountLoaded(accountEntity);
      }

      @Override public void onError(RetrofitError error) {
        accountDataCallback.onError(new RepositoryErrorBundle(error));
      }
    });
  }
}
