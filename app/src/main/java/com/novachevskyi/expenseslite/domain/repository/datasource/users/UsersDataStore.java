package com.novachevskyi.expenseslite.domain.repository.datasource.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.repository.datasource.RepositoryCallback;

public interface UsersDataStore {

  interface UserDataCallback extends RepositoryCallback {
    void onUserLoaded(UserEntity user);

    void onError(RepositoryErrorBundle error);
  }

  void getCurrentUser(UserDataCallback userCallback);

  void login(String userName, String password, UserDataCallback userCallback);

  void signUp(UserEntity userRegistrationEntity, UserDataCallback userCallback);
}
