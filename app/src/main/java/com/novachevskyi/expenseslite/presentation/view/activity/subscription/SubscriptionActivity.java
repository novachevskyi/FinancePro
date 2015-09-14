package com.novachevskyi.expenseslite.presentation.view.activity.subscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.activity.InAppPurchaseActivity;
import com.novachevskyi.expenseslite.presentation.view.fragment.subscription.SubscriptionFragment;

public class SubscriptionActivity extends InAppPurchaseActivity {

  private Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, SubscriptionActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);

    initialize();
  }

  private void initialize() {
    navigator = new Navigator();

    SubscriptionFragment subscriptionFragment = SubscriptionFragment.newInstance();
    subscriptionFragment.setOnSubscribeListener(subscribeListener);
    addFragment(R.id.fl_main, subscriptionFragment);

    initToolbar(getString(R.string.activity_subscription_subscription_toolbar_title));
  }

  private SubscriptionFragment.OnSubscribeListener subscribeListener =
      new SubscriptionFragment.OnSubscribeListener() {
        @Override public void onSubscribe() {
          becomePremium(new OnInAppPurchaseFinishedListener() {
            @Override public void inAppPurchasesFinished() {

            }
          });
        }
      };
}
