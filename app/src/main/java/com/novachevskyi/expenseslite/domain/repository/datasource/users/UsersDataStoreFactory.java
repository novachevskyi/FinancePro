package com.novachevskyi.expenseslite.domain.repository.datasource.users;

public class UsersDataStoreFactory {
  public UsersDataStore create() {
    UsersDataStore usersDataStore;

    usersDataStore = createCloudDataStore();

    return usersDataStore;
  }

  private UsersDataStore createCloudDataStore() {
    return new CloudUsersDataStore();
  }
}
