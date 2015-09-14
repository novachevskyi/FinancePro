package com.novachevskyi.expenseslite.domain.repository.datasource.transactions;

import com.novachevskyi.expenseslite.data.helpers.ObjectToJsonStringConverter;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionWhereAccountEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionWhereDateEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionWhereEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionWhereYearMonthDateEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.data.network.ApiConstants;
import com.novachevskyi.expenseslite.data.network.RestApi;
import com.novachevskyi.expenseslite.data.network.helpers.CurrentUserId;
import com.novachevskyi.expenseslite.data.network.services.transaction.TransactionsServiceImpl;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import retrofit.RetrofitError;

public class CloudTransactionsDataStore implements TransactionsDataStore {
  @Override public void create(TransactionEntity transactionEntity,
      final TransactionDataCallback transactionDataCallback) {
    if (transactionEntity != null) {
      transactionEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    }

    TransactionsServiceImpl transactionsService = RestApi.getInstance().getTransactionsService();
    transactionsService.create(transactionEntity,
        new TransactionsServiceImpl.TransactionCallback() {
          @Override public void onTransactionEntityLoaded(TransactionEntity transactionEntity) {
            transactionDataCallback.onTransactionLoaded(transactionEntity);
          }

          @Override public void onError(RetrofitError error) {
            transactionDataCallback.onError(new RepositoryErrorBundle(error));
          }
        });
  }

  @Override public void getList(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback) {
    Map<String, String> where = new HashMap<>();

    TransactionWhereEntity transactionWhereEntity = new TransactionWhereEntity();
    transactionWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();

    where.put(ApiConstants.WHERE_CLAUSE,
        ObjectToJsonStringConverter.convert(transactionWhereEntity));
    where.put(ApiConstants.ORDER_CLAUSE, String.format("-%s", ApiConstants.FIELD_PAYMENT_DATE));

    TransactionsServiceImpl transactionsService = RestApi.getInstance().getTransactionsService();
    transactionsService.getList(new TransactionsServiceImpl.TransactionsCallback() {
      @Override public void onTransactionEntitiesLoaded(TransactionsEntity transactionEntities) {
        transactionsDataCallback.onTransactionsLoaded(transactionEntities);
      }

      @Override public void onError(RetrofitError error) {
        transactionsDataCallback.onError(new RepositoryErrorBundle(error));
      }
    }, limit, skip, where);
  }

  @Override public void getList(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback, LocalDate localDate) {
    Map<String, String> where = new HashMap<>();

    TransactionWhereDateEntity transactionWhereEntity = new TransactionWhereDateEntity();
    transactionWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    transactionWhereEntity.paymentDate = localDate.toString();
    where.put(ApiConstants.WHERE_CLAUSE,
        ObjectToJsonStringConverter.convert(transactionWhereEntity));

    where.put(ApiConstants.ORDER_CLAUSE, String.format("-%s", ApiConstants.FIELD_PAYMENT_DATE));

    TransactionsServiceImpl transactionsService = RestApi.getInstance().getTransactionsService();
    transactionsService.getList(new TransactionsServiceImpl.TransactionsCallback() {
      @Override public void onTransactionEntitiesLoaded(TransactionsEntity transactionEntities) {
        transactionsDataCallback.onTransactionsLoaded(transactionEntities);
      }

      @Override public void onError(RetrofitError error) {
        transactionsDataCallback.onError(new RepositoryErrorBundle(error));
      }
    }, limit, skip, where);
  }

  @Override public void getListByMonth(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback, LocalDate date) {
    TransactionWhereYearMonthDateEntity transactionWhereYearMonthDateEntity =
        new TransactionWhereYearMonthDateEntity();
    transactionWhereYearMonthDateEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
    transactionWhereYearMonthDateEntity.yearAndMonth = formatter.print(date);
    transactionWhereYearMonthDateEntity.limit = limit;
    transactionWhereYearMonthDateEntity.skip = skip;

    TransactionsServiceImpl transactionsService = RestApi.getInstance().getTransactionsService();
    transactionsService.getListByMonth(new TransactionsServiceImpl.TransactionsCallback() {
      @Override public void onTransactionEntitiesLoaded(TransactionsEntity transactionEntities) {
        transactionsDataCallback.onTransactionsLoaded(transactionEntities);
      }

      @Override public void onError(RetrofitError error) {
        transactionsDataCallback.onError(new RepositoryErrorBundle(error));
      }
    }, transactionWhereYearMonthDateEntity);
  }

  @Override public void getListByAccount(int limit, int skip,
      final TransactionsDataCallback transactionsDataCallback, String filterId) {
    Map<String, String> where = new HashMap<>();

    TransactionWhereAccountEntity transactionWhereEntity = new TransactionWhereAccountEntity();
    transactionWhereEntity.userId = CurrentUserId.getInstance().getCurrentUserId();
    transactionWhereEntity.accountId = filterId;
    where.put(ApiConstants.WHERE_CLAUSE,
        ObjectToJsonStringConverter.convert(transactionWhereEntity));

    where.put(ApiConstants.ORDER_CLAUSE, String.format("-%s", ApiConstants.FIELD_PAYMENT_DATE));

    TransactionsServiceImpl transactionsService = RestApi.getInstance().getTransactionsService();
    transactionsService.getList(new TransactionsServiceImpl.TransactionsCallback() {
      @Override public void onTransactionEntitiesLoaded(TransactionsEntity transactionEntities) {
        transactionsDataCallback.onTransactionsLoaded(transactionEntities);
      }

      @Override public void onError(RetrofitError error) {
        transactionsDataCallback.onError(new RepositoryErrorBundle(error));
      }
    }, limit, skip, where);
  }
}
