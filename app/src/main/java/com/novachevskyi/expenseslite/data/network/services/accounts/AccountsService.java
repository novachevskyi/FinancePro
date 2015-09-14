package com.novachevskyi.expenseslite.data.network.services.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountWhereEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import java.util.Map;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface AccountsService {
  @GET("/1/classes/Account")
  public AccountsEntity getList(@Query("limit") int limit, @Query("skip") int skip, @QueryMap
  Map<String, String> where);

  @POST("/1/functions/currentTotalAmount")
  public AccountsTotalAmountEntity getTotalAmount(@Body AccountWhereEntity account);

  @POST("/1/classes/Account")
  public AccountEntity create(@Body AccountEntity account);
}
