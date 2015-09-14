package com.novachevskyi.expenseslite.presentation.view.activity.authorization;

import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.InAppPurchaseActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.authorization.SplashFragment;

public class SplashActivity extends InAppPurchaseActivity {

  private Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_no_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    SplashFragment splashFragment = SplashFragment.newInstance();
    splashFragment.setOnCurrentUserRequestListener(
        new SplashFragment.OnCurrentUserRequestListener() {
          @Override public void onUserLoadSucceed() {
            checkSubscription();
          }

          @Override public void onUserLoadFailed() {
            navigateToLoginScreen();
          }
        });
    addFragment(R.id.fl_main, splashFragment);
  }

  private void checkSubscription() {
    checkPremiumSubscription(new OnInAppPurchasesCheckedListener() {
      @Override public void inAppPurchasesChecked() {
        navigateToMainScreen();
      }
    });
  }

  private void navigateToMainScreen() {
    finish();
    navigator.navigateToMainScreen(SplashActivity.this);
  }

  private void navigateToLoginScreen() {
    finish();
    navigator.navigateToLoginScreen(SplashActivity.this);
  }
}
