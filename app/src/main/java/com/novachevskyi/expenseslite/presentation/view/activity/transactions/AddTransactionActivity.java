package com.novachevskyi.expenseslite.presentation.view.activity.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.ToolbarActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.transactions.AddTransactionFragment;

import static com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType.getTransactionType;

public class AddTransactionActivity extends ToolbarActivity {

  public static final String ADD_TRANSACTION_ACTIVITY_TYPE_KEY =
      "add_transaction_activity_type_key";

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, AddTransactionActivity.class);
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
    int transactionType = intent.getIntExtra(ADD_TRANSACTION_ACTIVITY_TYPE_KEY, 0);

    AddTransactionFragment addTransactionFragment =
        AddTransactionFragment.newInstance(transactionType);
    addTransactionFragment.setOnTransactionCreatedListener(
        new AddTransactionFragment.OnTransactionCreatedListener() {
          @Override public void onTransactionCreated(TransactionModel transactionModel) {
            navigateBack();
          }
        });
    addFragment(R.id.fl_main, addTransactionFragment);

    switch (getTransactionType(transactionType)) {
      case INCOME:
        initToolbar(getString(R.string.activity_add_transaction_new_income_toolbar_title));
        break;
      case OUTCOME:
        initToolbar(getString(R.string.activity_add_transaction_new_outcome_toolbar_title));
        break;
      default:
        initToolbar(getString(R.string.activity_add_transaction_new_transaction_toolbar_title));
        break;
    }
  }
}
