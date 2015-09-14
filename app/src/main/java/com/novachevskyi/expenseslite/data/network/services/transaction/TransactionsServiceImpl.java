package com.novachevskyi.expenseslite.data.network.services.transaction;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionWhereYearMonthDateEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsHolderEntity;
import java.util.Map;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class TransactionsServiceImpl {

  public interface TransactionCallback {
    void onTransactionEntityLoaded(TransactionEntity transactionEntity);

    void onError(RetrofitError error);
  }

  public interface TransactionsCallback {
    void onTransactionEntitiesLoaded(TransactionsEntity transactionEntities);

    void onError(RetrofitError error);
  }

  private TransactionsService transactionsService;

  public TransactionsServiceImpl(RestAdapter restAdapter) {
    transactionsService = restAdapter.create(TransactionsService.class);
  }

  public void create(TransactionEntity transactionEntity, TransactionCallback transactionCallback) {
    try {
      TransactionEntity transactionEntityResult =
          this.transactionsService.create(transactionEntity);
      transactionCallback.onTransactionEntityLoaded(transactionEntityResult);
    } catch (RetrofitError error) {
      transactionCallback.onError(error);
    }
  }

  public void getList(TransactionsCallback transactionsCallback, int limit, int skip,
      Map<String, String> where) {
    try {
      TransactionsEntity transactionsEntity = this.transactionsService.getList(limit, skip, where);
      transactionsCallback.onTransactionEntitiesLoaded(transactionsEntity);
    } catch (RetrofitError error) {
      transactionsCallback.onError(error);
    }
  }

  public void getListByMonth(TransactionsCallback transactionsCallback,
      TransactionWhereYearMonthDateEntity transactionWhereYearMonthDateEntity) {
    try {
      TransactionsHolderEntity transactionsHolderEntity = this.transactionsService.getListByMonth(
          transactionWhereYearMonthDateEntity);
      TransactionsEntity transactionsEntity = new TransactionsEntity();
      transactionsEntity.results = transactionsHolderEntity.result;
      transactionsCallback.onTransactionEntitiesLoaded(transactionsEntity);
    } catch (RetrofitError error) {
      transactionsCallback.onError(error);
    }
  }
}
