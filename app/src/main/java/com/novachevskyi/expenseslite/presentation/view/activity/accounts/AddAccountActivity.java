package com.novachevskyi.expenseslite.presentation.view.activity.accounts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.ToolbarActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.accounts.AddAccountFragment;

public class AddAccountActivity extends ToolbarActivity {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, AddAccountActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    AddAccountFragment addAccountFragment = AddAccountFragment.newInstance();
    addAccountFragment.setOnAccountCreatedListener(
        new AddAccountFragment.OnAccountCreatedListener() {
          @Override public void onAccountCreated(AccountModel accountModel) {
            navigateBack();
          }
        });
    addFragment(R.id.fl_main, addAccountFragment);

    initToolbar(getString(R.string.activity_add_account_new_account_toolbar_title));
  }
}
