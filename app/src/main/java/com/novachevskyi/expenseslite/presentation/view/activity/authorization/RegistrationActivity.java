package com.novachevskyi.expenseslite.presentation.view.activity.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.InAppPurchaseActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.authorization.RegistrationFragment;

public class RegistrationActivity extends InAppPurchaseActivity {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, RegistrationActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    RegistrationFragment registrationFragment = RegistrationFragment.newInstance();
    registrationFragment.setOnCurrentUserRequestListener(
        new RegistrationFragment.OnCurrentUserRequestListener() {
          @Override public void onUserLoadSucceed() {
            checkSubscription();
          }

          @Override public void onUserLoadFailed() {

          }
        });
    addFragment(R.id.fl_main, registrationFragment);

    initToolbar(getString(R.string.activity_registration_registration_toolbar_title));
  }

  private void checkSubscription() {
    checkPremiumSubscription(new InAppPurchaseActivity.OnInAppPurchasesCheckedListener() {
      @Override public void inAppPurchasesChecked() {
        navigateToMainScreen();
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        navigateToLoginScreen();
        break;
    }
    return true;
  }

  private void navigateToLoginScreen() {
    finish();
    navigator.navigateToLoginScreen(RegistrationActivity.this);
  }

  private void navigateToMainScreen() {
    finish();
    navigator.navigateToMainScreen(RegistrationActivity.this);
  }

  @Override
  public void onBackPressed() {
    navigateToLoginScreen();
  }
}
