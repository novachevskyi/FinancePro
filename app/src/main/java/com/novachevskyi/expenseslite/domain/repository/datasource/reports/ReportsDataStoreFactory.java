package com.novachevskyi.expenseslite.domain.repository.datasource.reports;

public class ReportsDataStoreFactory {
  public ReportsDataStore create() {
    ReportsDataStore reportsDataStore;

    reportsDataStore = createCloudDataStore();

    return reportsDataStore;
  }

  private ReportsDataStore createCloudDataStore() {
    return new CloudReportsDataStore();
  }
}
