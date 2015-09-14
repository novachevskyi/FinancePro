package com.novachevskyi.expenseslite.presentation.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.data.network.helpers.CredentialsHelper;
import com.novachevskyi.expenseslite.presentation.model.main.DrawerMenuType;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.component.FabMainView;
import com.novachevskyi.expenseslite.presentation.view.fragment.accounts.AccountsListFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.budgets.BudgetsListFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.main.DrawerMenuFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.reports.CategoriesMonthReportFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.reports.GeneralReportFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.reports.ReportsMonthListFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.transactions.TransactionsListFragment;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;
import com.novachevskyi.expenseslite.presentation.view.helper.SharedPreferencesHelper;
import com.novachevskyi.rateapp.AppRate;

public class MainActivity extends ToolbarActivity {

  private static final String MAIN_ACTIVITY_CURRENT_SELECTED_FRAGMENT_PREFERENCE =
      "main_activity_current_selected_fragment_preference";

  private static final int MIN_DAYS_BEFORE_ASK_FOR_RATE = 3;
  private static final int MIN_LAUNCHES_BEFORE_ASK_FOR_RATE = 20;

  private Navigator navigator;

  private DrawerMenuType drawerMenuType;

  private DrawerLayout drawer;

  private AlertDialog logoutDialog;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drawer);

    initialize();
    initializeAppRater();
  }

  private void initializeAppRater() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle(String.format(getString(R.string.activity_main_rate_title), getString(R.string.app_name)))
        .setMessage(String.format(
            getString(R.string.activity_main_if_you_enjoy_rate_please),
            getString(R.string.app_name)))
        .setPositiveButton(getString(R.string.activity_main_rate_button_title), null)
        .setNegativeButton(getString(R.string.activity_main_no_thanks_button_title), null)
        .setNeutralButton(getString(R.string.activity_main_later_button_title), null);

    new AppRate(this)
        .setCustomDialog(builder)
        .setMinDaysUntilPrompt(MIN_DAYS_BEFORE_ASK_FOR_RATE)
        .setMinLaunchesUntilPrompt(MIN_LAUNCHES_BEFORE_ASK_FOR_RATE)
        .setShowIfAppHasCrashed(false)
        .init();
  }

  private void initialize() {
    navigator = new Navigator();

    String title = setDefaultFragment();

    initToolbar(title);

    initDrawer();
    initFabMainView();
  }

  private void initFabMainView() {
    FabMainView fabMainView = (FabMainView) findViewById(R.id.fab_main_view);
    fabMainView.setOnFabMainViewButtonClickListener(
        new FabMainView.OnFabMainViewButtonClickListener() {
          @Override public void onAccountAdd() {
            navigator.navigateToAddAccountScreen(MainActivity.this);
          }

          @Override public void onTransactionIncome() {
            navigator.navigateToAddTransactionScreen(MainActivity.this, TransactionType.INCOME);
          }

          @Override public void onTransactionPay() {
            navigator.navigateToAddTransactionScreen(MainActivity.this, TransactionType.OUTCOME);
          }

          @Override public void onTransactionTransfer() {
            navigator.navigateToAddTransactionTransferScreen(MainActivity.this);
          }

          @Override public void onBudgetAdd() {
            navigator.navigateToAddBudgetScreen(MainActivity.this);
          }
        });
  }

  private void initDrawer() {
    drawer = (DrawerLayout) findViewById(R.id.drawer);

    ActionBarDrawerToggle actionBarDrawerToggle =
        new ActionBarDrawerToggle(this, drawer, getToolbar(), R.string.activity_main_drawer_open,
            R.string.activity_main_drawer_close);
    actionBarDrawerToggle.syncState();

    drawer.setDrawerListener(actionBarDrawerToggle);

    final DrawerMenuFragment drawerMenuFragment = DrawerMenuFragment.newInstance();
    drawerMenuFragment.setOnItemClickListener(new DrawerMenuFragment.OnItemClickListener() {
      @Override public void onDrawerMenuItemClicked(DrawerMenuType drawerMenuType) {
        setFragmentWithDrawerMenuType(drawerMenuType);

        drawer.closeDrawers();
      }

      @Override public void onLogout() {
        drawer.closeDrawers();

        logout();
      }

      @Override public void onPremium() {
        drawer.closeDrawers();

        navigator.navigateToSubscriptionScreen(MainActivity.this);
      }
    });
    addFragment(R.id.fl_drawer, drawerMenuFragment);
  }

  private void setFragmentWithDrawerMenuType(DrawerMenuType drawerMenuType) {
    switch (drawerMenuType) {
      case ACCOUNTS:
        replaceWithAccounts();
        break;
      case TRANSACTIONS:
        replaceWithTransactions();
        break;
      case BUDGETS:
        replaceWithBudgets();
        break;
      case GENERAL_REPORT:
        replaceWithGeneralReport();
        break;
      case YEAR_REPORT:
        replaceWithYearReport();
        break;
      case CATEGORIES_REPORT:
        replaceWithCategoriesMonthReport();
        break;
    }

    SharedPreferencesHelper.setInt(MainActivity.this,
        MAIN_ACTIVITY_CURRENT_SELECTED_FRAGMENT_PREFERENCE, drawerMenuType.getNumericType());
  }

  private void logout() {
    logoutDialog = DialogHelper.constructAlertDialogWithYesNoButton(MainActivity.this,
        getString(R.string.activity_main_logout_title),
        getString(R.string.activity_main_are_you_sure_logout_title),
        new DialogHelper.OnDialogYesNoButtonClickedListener() {
          @Override public void onYesClick() {
            logoutDialog.dismiss();

            MainActivity.this.invokeLogout();
          }

          @Override public void onNoClick() {
            logoutDialog.dismiss();
          }
        });
    logoutDialog.show();
  }

  private void invokeLogout() {
    CredentialsHelper.logout();
    finish();
    Intent i = getBaseContext().getPackageManager()
        .getLaunchIntentForPackage(getBaseContext().getPackageName());
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(i);
  }

  private void replaceWithCategoriesMonthReport() {
    if (drawerMenuType != null && drawerMenuType != DrawerMenuType.CATEGORIES_REPORT) {
      replaceFragment(R.id.fl_main, CategoriesMonthReportFragment.newInstance());
      drawerMenuType = DrawerMenuType.CATEGORIES_REPORT;
      setToolbarTitle(getString(R.string.activity_main_categories_toolbar_title));
    }
  }

  private void replaceWithYearReport() {
    if (drawerMenuType != null && drawerMenuType != DrawerMenuType.YEAR_REPORT) {
      replaceFragment(R.id.fl_main, ReportsMonthListFragment.newInstance());
      drawerMenuType = DrawerMenuType.YEAR_REPORT;
      setToolbarTitle(getString(R.string.activity_main_calendar_toolbar_title));
    }
  }

  private void replaceWithGeneralReport() {
    if (drawerMenuType != null && drawerMenuType != DrawerMenuType.GENERAL_REPORT) {
      replaceFragment(R.id.fl_main, GeneralReportFragment.newInstance());
      drawerMenuType = DrawerMenuType.GENERAL_REPORT;
      setToolbarTitle(getString(R.string.activity_main_general_report_toolbar_title));
    }
  }

  private void replaceWithAccounts() {
    if (drawerMenuType != null && drawerMenuType != DrawerMenuType.ACCOUNTS) {
      replaceFragment(R.id.fl_main, AccountsListFragment.newInstance());
      drawerMenuType = DrawerMenuType.ACCOUNTS;
      setToolbarTitle(getString(R.string.activity_main_accounts_toolbar_title));
    }
  }

  private void replaceWithTransactions() {
    if (drawerMenuType != null && drawerMenuType != DrawerMenuType.TRANSACTIONS) {
      replaceFragment(R.id.fl_main, TransactionsListFragment.newInstance());
      drawerMenuType = DrawerMenuType.TRANSACTIONS;
      setToolbarTitle(getString(R.string.activity_main_transactions_toolbar_title));
    }
  }

  private void replaceWithBudgets() {
    if (drawerMenuType != null && drawerMenuType != DrawerMenuType.BUDGETS) {
      replaceFragment(R.id.fl_main, BudgetsListFragment.newInstance());
      drawerMenuType = DrawerMenuType.BUDGETS;
      setToolbarTitle(getString(R.string.activity_main_budgets_toolbar_title));
    }
  }

  private String setDefaultFragment() {
    int selectedFragment = SharedPreferencesHelper.getInt(MainActivity.this,
        MAIN_ACTIVITY_CURRENT_SELECTED_FRAGMENT_PREFERENCE, 0);
    DrawerMenuType drawerMenuType = DrawerMenuType.getTransactionType(selectedFragment);

    String title = null;

    switch (drawerMenuType) {
      case ACCOUNTS:
        addFragment(R.id.fl_main, AccountsListFragment.newInstance());
        this.drawerMenuType = DrawerMenuType.ACCOUNTS;
        title = getString(R.string.activity_main_accounts_toolbar_title);
        break;
      case TRANSACTIONS:
        addFragment(R.id.fl_main, TransactionsListFragment.newInstance());
        this.drawerMenuType = DrawerMenuType.TRANSACTIONS;
        title = getString(R.string.activity_main_transactions_toolbar_title);
        break;
      case BUDGETS:
        addFragment(R.id.fl_main, BudgetsListFragment.newInstance());
        this.drawerMenuType = DrawerMenuType.BUDGETS;
        title = getString(R.string.activity_main_budgets_toolbar_title);
        break;
      case GENERAL_REPORT:
        addFragment(R.id.fl_main, GeneralReportFragment.newInstance());
        this.drawerMenuType = DrawerMenuType.GENERAL_REPORT;
        title = getString(R.string.activity_main_general_report_toolbar_title);
        break;
      case YEAR_REPORT:
        addFragment(R.id.fl_main, ReportsMonthListFragment.newInstance());
        this.drawerMenuType = DrawerMenuType.YEAR_REPORT;
        title = getString(R.string.activity_main_calendar_toolbar_title);
        break;
      case CATEGORIES_REPORT:
        addFragment(R.id.fl_main, CategoriesMonthReportFragment.newInstance());
        this.drawerMenuType = DrawerMenuType.CATEGORIES_REPORT;
        title = getString(R.string.activity_main_categories_toolbar_title);
        break;
    }

    setToolbarTitle(title);

    return title;
  }
}
