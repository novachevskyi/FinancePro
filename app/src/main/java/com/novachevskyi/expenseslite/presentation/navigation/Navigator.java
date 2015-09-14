package com.novachevskyi.expenseslite.presentation.navigation;

import android.app.Activity;
import android.content.Intent;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import com.novachevskyi.expenseslite.presentation.view.activity.MainActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.accounts.AddAccountActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.authorization.LoginActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.authorization.RegistrationActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.budgets.AddBudgetActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.subscription.SubscriptionActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.transactions.AddTransactionActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.transactions.AddTransactionTransferActivity;
import com.novachevskyi.expenseslite.presentation.view.activity.transactions.TransactionsListActivity;

public class Navigator {

  public void navigateToLoginScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = LoginActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToRegistrationScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = RegistrationActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToMainScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = MainActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToAddAccountScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = AddAccountActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToAddTransactionScreen(Activity activity, TransactionType transactionType) {
    if (activity != null) {
      Intent intentToLaunch = AddTransactionActivity.getCallingIntent(activity);
      intentToLaunch.putExtra(AddTransactionActivity.ADD_TRANSACTION_ACTIVITY_TYPE_KEY,
          transactionType.getNumericType());
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToAddTransactionTransferScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = AddTransactionTransferActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToAddBudgetScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = AddBudgetActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToTransactionsListScreen(Activity activity,
      TransactionFilterType transactionFilterType) {
    if (activity != null) {
      Intent intentToLaunch = TransactionsListActivity.getCallingIntent(activity);
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_FILTER_ACTIVITY_TYPE_KEY,
          transactionFilterType.getNumericType());
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToTransactionsByMonthListScreen(Activity activity,
      int month) {
    if (activity != null) {
      Intent intentToLaunch = TransactionsListActivity.getCallingIntent(activity);
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_FILTER_ACTIVITY_TYPE_KEY,
          TransactionFilterType.MONTH.getNumericType());
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_MONTH_ACTIVITY_TYPE_KEY,
          month);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToTransactionsByAccountListScreen(Activity activity,
      String accountId) {
    if (activity != null) {
      Intent intentToLaunch = TransactionsListActivity.getCallingIntent(activity);
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_FILTER_ACTIVITY_TYPE_KEY,
          TransactionFilterType.ACCOUNT.getNumericType());
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_ACCOUNT_ID_ACTIVITY_TYPE_KEY,
          accountId);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToTransactionsByBudgetListScreen(Activity activity,
      String budgetId) {
    if (activity != null) {
      Intent intentToLaunch = TransactionsListActivity.getCallingIntent(activity);
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_FILTER_ACTIVITY_TYPE_KEY,
          TransactionFilterType.ACCOUNT.getNumericType());
      intentToLaunch.putExtra(TransactionsListActivity.TRANSACTION_BUDGET_ID_ACTIVITY_TYPE_KEY,
          budgetId);
      activity.startActivity(intentToLaunch);
    }
  }

  public void navigateToSubscriptionScreen(Activity activity) {
    if (activity != null) {
      Intent intentToLaunch = SubscriptionActivity.getCallingIntent(activity);
      activity.startActivity(intentToLaunch);
    }
  }
}
