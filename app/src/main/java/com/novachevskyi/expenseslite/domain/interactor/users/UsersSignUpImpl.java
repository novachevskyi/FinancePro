package com.novachevskyi.expenseslite.domain.interactor.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;

public class UsersSignUpImpl extends UsersBaseImpl {

  private UserEntity userEntity;

  public UsersSignUpImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);
  }

  public void execute(UsersDataStore.UserDataCallback callback, UserEntity userEntity) {
    this.userEntity = userEntity;

    executeBase(callback);
  }

  @Override public void run() {
    userRepository.signUp(userEntity, repositoryCallback);
  }
}
