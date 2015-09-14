package com.novachevskyi.expenseslite.presentation.view.activity.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.ToolbarActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.transactions.TransactionsListFragment;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType.ACCOUNT;
import static com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType.BUDGET;
import static com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType.MONTH;
import static com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType.getTransactionFilterType;

public class TransactionsListActivity extends ToolbarActivity {

  public static final String TRANSACTION_FILTER_ACTIVITY_TYPE_KEY =
      "transaction_filter_activity_type_key";
  public static final String TRANSACTION_MONTH_ACTIVITY_TYPE_KEY =
      "transaction_month_activity_type_key";
  public static final String TRANSACTION_ACCOUNT_ID_ACTIVITY_TYPE_KEY =
      "transaction_account_id_activity_type_key";
  public static final String TRANSACTION_BUDGET_ID_ACTIVITY_TYPE_KEY =
      "transaction_budget_id_activity_type_key";

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, TransactionsListActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    Intent intent = getIntent();
    int transactionFilterType = intent.getIntExtra(TRANSACTION_FILTER_ACTIVITY_TYPE_KEY, 0);
    int month = intent.getIntExtra(TRANSACTION_MONTH_ACTIVITY_TYPE_KEY, 0);

    TransactionsListFragment transactionFragment = null;
    if (transactionFilterType == MONTH.getNumericType()) {
      transactionFragment = TransactionsListFragment.newInstance(transactionFilterType, month);
    }
    else if(transactionFilterType == ACCOUNT.getNumericType()) {
      String accountId = intent.getStringExtra(TRANSACTION_ACCOUNT_ID_ACTIVITY_TYPE_KEY);
      transactionFragment = TransactionsListFragment.newInstance(transactionFilterType, accountId);
    }
    else if(transactionFilterType == BUDGET.getNumericType()) {
      String budgetId = intent.getStringExtra(TRANSACTION_BUDGET_ID_ACTIVITY_TYPE_KEY);
      transactionFragment = TransactionsListFragment.newInstance(transactionFilterType, budgetId);
    }
    else {
      transactionFragment = TransactionsListFragment.newInstance(transactionFilterType);
    }
    addFragment(R.id.fl_main, transactionFragment);

    switch (getTransactionFilterType(transactionFilterType)) {
      case TODAY:
        initToolbar(getString(R.string.activity_transactions_list_today_toolbar_title));
        break;
      case THIS_MONTH:
        initToolbar(getString(R.string.activity_transactions_list_this_month_toolbar_title));
        break;
      case MONTH:
        LocalDate localDate = new LocalDate();
        LocalDate transactionMonthFilter =
            new LocalDate(localDate.getYear(), month + 1,
                month + 1);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(
            getString(R.string.activity_transactions_month_date_format));
        String formattedDate = formatter.print(transactionMonthFilter);
        initToolbar(formattedDate);
        break;
      case ACCOUNT:
        initToolbar(getString(R.string.activity_transactions_list_account_toolbar_title));
        break;
      case BUDGET:
        initToolbar(getString(R.string.activity_transactions_list_budget_toolbar_title));
        break;
      default:
        initToolbar(getString(R.string.activity_transactions_list_transactions_title));
        break;
    }
  }
}
