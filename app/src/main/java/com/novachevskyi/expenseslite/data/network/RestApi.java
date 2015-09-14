package com.novachevskyi.expenseslite.data.network;

import com.novachevskyi.expenseslite.data.network.services.accounts.AccountsServiceImpl;
import com.novachevskyi.expenseslite.data.network.services.budgets.BudgetsServiceImpl;
import com.novachevskyi.expenseslite.data.network.services.reports.ReportsServiceImpl;
import com.novachevskyi.expenseslite.data.network.services.transaction.TransactionsServiceImpl;
import com.novachevskyi.expenseslite.data.network.services.users.UsersServiceImpl;

public class RestApi extends RestApiBase {

  private static RestApi instance;

  public static RestApi getInstance() {
    if (instance == null) {
      synchronized (RestApi.class) {
        if (instance == null) {
          instance = new RestApi();
        }
      }
    }
    return instance;
  }

  private RestApi() {
    super();
  }

  public UsersServiceImpl getUsersService() {
    return new UsersServiceImpl(restAdapter);
  }

  public AccountsServiceImpl getAccountsService() {
    return new AccountsServiceImpl(restAdapter);
  }

  public TransactionsServiceImpl getTransactionsService() {
    return new TransactionsServiceImpl(restAdapter);
  }

  public BudgetsServiceImpl getBudgetsService() {
    return new BudgetsServiceImpl(restAdapter);
  }

  public ReportsServiceImpl getReportsService() {
    return new ReportsServiceImpl(restAdapter);
  }
}
