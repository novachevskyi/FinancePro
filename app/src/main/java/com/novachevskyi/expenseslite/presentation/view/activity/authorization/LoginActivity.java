package com.novachevskyi.expenseslite.presentation.view.activity.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.InAppPurchaseActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.authorization.LoginFragment;
import com.novachevskyi.expenseslite.presentation.view.fragment.authorization.UserRequestBaseFragment;

public class LoginActivity extends InAppPurchaseActivity {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_no_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    LoginFragment loginFragment = LoginFragment.newInstance();
    loginFragment.setOnCurrentUserRequestListener(
        new UserRequestBaseFragment.OnCurrentUserRequestListener() {
          @Override public void onUserLoadSucceed() {
            checkSubscription();
          }

          @Override public void onUserLoadFailed() {

          }
        });
    loginFragment.setOnRegistrationRequestListener(
        new LoginFragment.OnRegistrationRequestListener() {
          @Override public void onRegistration() {
            navigateToRegistrationScreen();
          }
        });
    addFragment(R.id.fl_main, loginFragment);
  }

  private void checkSubscription() {
    checkPremiumSubscription(new InAppPurchaseActivity.OnInAppPurchasesCheckedListener() {
      @Override public void inAppPurchasesChecked() {
        navigateToMainScreen();
      }
    });
  }

  private void navigateToRegistrationScreen() {
    finish();
    navigator.navigateToRegistrationScreen(LoginActivity.this);
  }

  private void navigateToMainScreen() {
    finish();
    navigator.navigateToMainScreen(LoginActivity.this);
  }
}
