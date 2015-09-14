package com.novachevskyi.expenseslite.presentation.view.activity.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.ToolbarActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.transactions.AddTransactionTransferFragment;

public class AddTransactionTransferActivity extends ToolbarActivity {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, AddTransactionTransferActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    AddTransactionTransferFragment addTransactionFragment =
        AddTransactionTransferFragment.newInstance();
    addTransactionFragment.setOnTransactionCreatedListener(
        new AddTransactionTransferFragment.OnTransactionCreatedListener() {
          @Override public void onTransactionCreated(TransactionModel sourceTransactionModel,
              TransactionModel destinationTransactionModel) {
            navigateBack();
          }
        });
    addFragment(R.id.fl_main, addTransactionFragment);

    initToolbar(getString(R.string.activity_add_transaction_transfer_new_transfer_transaction));
  }
}
