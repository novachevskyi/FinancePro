package com.novachevskyi.expenseslite.presentation.view.activity.budgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.ToolbarActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.budgets.AddBudgetFragment;

public class AddBudgetActivity extends ToolbarActivity {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, AddBudgetActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    AddBudgetFragment addBudgetFragment =
        AddBudgetFragment.newInstance();
    addBudgetFragment.setOnBudgetCreatedListener(
        new AddBudgetFragment.OnBudgetCreatedListener() {
          @Override public void onBudgetCreated(BudgetModel transactionModel) {
            navigateBack();
          }
        });
    addFragment(R.id.fl_main, addBudgetFragment);

    initToolbar(getString(R.string.activity_add_budget_new_budget_toolbar_title));
  }
}
