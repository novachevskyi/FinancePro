package com.novachevskyi.expenseslite.data.network.services.transaction;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionWhereYearMonthDateEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsHolderEntity;
import java.util.Map;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface TransactionsService {
  @GET("/1/classes/Transaction")
  public TransactionsEntity getList(@Query("limit") int limit, @Query("skip") int skip, @QueryMap
  Map<String, String> where);

  @POST("/1/classes/Transaction")
  public TransactionEntity create(@Body TransactionEntity transaction);

  @POST("/1/functions/getTransactionsByMonth")
  public TransactionsHolderEntity getListByMonth(@Body TransactionWhereYearMonthDateEntity transaction);
}
