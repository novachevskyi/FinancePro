package com.novachevskyi.expenseslite.domain.repository.datasource.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.data.network.RestApi;
import com.novachevskyi.expenseslite.data.network.helpers.CredentialsHelper;
import com.novachevskyi.expenseslite.data.network.services.users.UsersServiceImpl;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import retrofit.RetrofitError;

public class CloudUsersDataStore implements UsersDataStore {

  @Override public void getCurrentUser(final UserDataCallback userCallback) {
    UsersServiceImpl usersService = RestApi.getInstance().getUsersService();
    usersService.getCurrentUser(new UsersServiceImpl.UserCallback() {
      @Override public void onUserEntityLoaded(UserEntity userEntity) {
        userCallback.onUserLoaded(userEntity);
      }

      @Override public void onError(RetrofitError error) {
        userCallback.onError(new RepositoryErrorBundle(error));
      }
    });
  }

  @Override public void login(String userName, String password,
      final UserDataCallback userCallback) {
    UsersServiceImpl usersService = RestApi.getInstance().getUsersService();
    usersService.login(userName, password, new UsersServiceImpl.UserCallback() {
      @Override public void onUserEntityLoaded(UserEntity userEntity) {
        userCallback.onUserLoaded(userEntity);

        CredentialsHelper.setCurrentUserCredentials(userEntity);
      }

      @Override public void onError(RetrofitError error) {
        userCallback.onError(new RepositoryErrorBundle(error));
      }
    });
  }

  @Override public void signUp(UserEntity userRegistrationEntity,
      final UserDataCallback userCallback) {
    UsersServiceImpl usersService = RestApi.getInstance().getUsersService();
    usersService.signUp(userRegistrationEntity, new UsersServiceImpl.UserCallback() {
      @Override public void onUserEntityLoaded(UserEntity userEntity) {
        userCallback.onUserLoaded(userEntity);

        CredentialsHelper.setCurrentUserCredentials(userEntity);
      }

      @Override public void onError(RetrofitError error) {
        userCallback.onError(new RepositoryErrorBundle(error));
      }
    });
  }
}
