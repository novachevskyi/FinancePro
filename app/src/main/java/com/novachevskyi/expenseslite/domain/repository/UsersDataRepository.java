package com.novachevskyi.expenseslite.domain.repository;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStoreFactory;

public class UsersDataRepository implements UsersDataStore {

  private static UsersDataRepository instance;

  public static UsersDataRepository getInstance() {
    if (instance == null) {
      synchronized (UsersDataRepository.class) {
        if (instance == null) {
          instance = new UsersDataRepository();
        }
      }
    }
    return instance;
  }

  private final UsersDataStoreFactory userDataStoreFactory;

  protected UsersDataRepository() {
    this.userDataStoreFactory = new UsersDataStoreFactory();
  }

  @Override public void getCurrentUser(final UserDataCallback userCallback) {
    final UsersDataStore userDataStore = this.userDataStoreFactory.create();
    userDataStore.getCurrentUser(new UserDataCallback() {
      @Override public void onUserLoaded(UserEntity user) {
        userCallback.onUserLoaded(user);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        userCallback.onError(error);
      }
    });
  }

  @Override public void login(String userName, String password,
      final UserDataCallback userCallback) {
    final UsersDataStore userDataStore = this.userDataStoreFactory.create();
    userDataStore.login(userName, password, new UserDataCallback() {
      @Override public void onUserLoaded(UserEntity user) {
        userCallback.onUserLoaded(user);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        userCallback.onError(error);
      }
    });
  }

  @Override public void signUp(UserEntity userRegistrationEntity,
      final UserDataCallback userCallback) {
    final UsersDataStore userDataStore = this.userDataStoreFactory.create();
    userDataStore.signUp(userRegistrationEntity, new UserDataCallback() {
      @Override public void onUserLoaded(UserEntity user) {
        userCallback.onUserLoaded(user);
      }

      @Override public void onError(RepositoryErrorBundle error) {
        userCallback.onError(error);
      }
    });
  }
}
