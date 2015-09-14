package com.novachevskyi.expenseslite.data.network.services.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class UsersServiceImpl {

  public interface UserCallback {
    void onUserEntityLoaded(UserEntity userEntity);

    void onError(RetrofitError error);
  }

  private UsersService usersService;

  public UsersServiceImpl(RestAdapter restAdapter) {
    usersService = restAdapter.create(UsersService.class);
  }

  public void getCurrentUser(UserCallback userCallback) {
    try {
      UserEntity userEntity = this.usersService.getCurrentUser();
      userCallback.onUserEntityLoaded(userEntity);
    } catch (RetrofitError error) {
      userCallback.onError(error);
    }
  }

  public void signUp(UserEntity userRegistrationEntity, UserCallback userCallback) {
    try {
      UserEntity userEntity = this.usersService.signUp(userRegistrationEntity);
      userCallback.onUserEntityLoaded(userEntity);
    } catch (RetrofitError error) {
      userCallback.onError(error);
    }
  }

  public void login(String userName, String password, UserCallback userCallback) {
    try {
      UserEntity userEntity = this.usersService.login(userName, password);
      userCallback.onUserEntityLoaded(userEntity);
    } catch (RetrofitError error) {
      userCallback.onError(error);
    }
  }
}
