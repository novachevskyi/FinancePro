package com.novachevskyi.expenseslite.domain.interactor.users;

import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;

public class UsersLoginImpl extends UsersBaseImpl {

  private String userName;
  private String password;

  public UsersLoginImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);
  }

  public void execute(UsersDataStore.UserDataCallback callback, String userName, String password) {
    this.userName = userName;
    this.password = password;

    executeBase(callback);
  }

  @Override public void run() {
    userRepository.login(userName, password, repositoryCallback);
  }
}
