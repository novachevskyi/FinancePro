package com.novachevskyi.expenseslite.data.network.services.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UsersService {
  @GET("/1/users/me")
  public UserEntity getCurrentUser();

  @POST("/1/users")
  public UserEntity signUp(@Body UserEntity user);

  @GET("/1/login")
  public UserEntity login(@Query("username") String username, @Query("password") String password);
}
