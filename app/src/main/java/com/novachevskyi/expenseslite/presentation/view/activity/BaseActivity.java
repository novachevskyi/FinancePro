package com.novachevskyi.expenseslite.presentation.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public abstract class BaseActivity extends ActionBarActivity {
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  protected void replaceFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
    transaction.replace(containerViewId, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  protected void navigateBack() {
    Intent upIntent = NavUtils.getParentActivityIntent(this);
    if(upIntent != null) {
      if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
        TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(upIntent)
            .startActivities();
      } else {
        NavUtils.navigateUpTo(this, upIntent);
      }
    } else {
      finish();
    }
  }

  protected void showToastMessage(String message) {
    Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onBackPressed() {
    navigateBack();
  }
}
