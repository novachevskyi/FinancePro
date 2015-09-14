package com.novachevskyi.expenseslite.domain.interactor.users;

import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;

public class UsersGetCurrentUserImpl extends UsersBaseImpl {

  public UsersGetCurrentUserImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);
  }

  @Override public void run() {
    userRepository.getCurrentUser(repositoryCallback);
  }

  public void execute(UsersDataStore.UserDataCallback callback) {
    executeBase(callback);
  }
}
