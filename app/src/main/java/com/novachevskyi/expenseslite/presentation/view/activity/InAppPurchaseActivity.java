package com.novachevskyi.expenseslite.presentation.view.activity;

import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.data.network.helpers.CurrentUserId;
import com.novachevskyi.expenseslite.presentation.utils.AppConstants;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.utils.purchases.utils.IabHelper;
import com.novachevskyi.expenseslite.presentation.utils.purchases.utils.IabResult;
import com.novachevskyi.expenseslite.presentation.utils.purchases.utils.Inventory;
import com.novachevskyi.expenseslite.presentation.utils.purchases.utils.Purchase;

public abstract class InAppPurchaseActivity extends ToolbarActivity {

  private static final String SKU_PREMIUM = "premium";

  private static final int RC_REQUEST = 10001;

  private IabHelper helper;

  public interface OnInAppPurchasesCheckedListener {
    void inAppPurchasesChecked();
  }

  public interface OnInAppPurchaseFinishedListener {
    void inAppPurchasesFinished();
  }

  private OnInAppPurchaseFinishedListener onInAppPurchaseFinishedListener;

  private OnInAppPurchasesCheckedListener onInAppPurchasesCheckedListener;

  protected void checkPremiumSubscription(
      OnInAppPurchasesCheckedListener onInAppPurchasesCheckedListener) {
    this.onInAppPurchasesCheckedListener = onInAppPurchasesCheckedListener;

    initHelper();

    helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
      public void onIabSetupFinished(IabResult result) {
        if (!result.isSuccess()) {
          notifyFinished();
          return;
        }

        if (helper == null) {
          notifyFinished();
          return;
        }

        helper.queryInventoryAsync(mGotInventoryListener);
      }
    });
  }

  private void initHelper() {
    helper = null;

    helper = new IabHelper(this, AppConstants.getInAppPurchasesPublicKey());
    helper.enableDebugLogging(false);
  }

  private void notifyFinished() {
    if (onInAppPurchasesCheckedListener != null) {
      onInAppPurchasesCheckedListener.inAppPurchasesChecked();
    }
  }

  private IabHelper.QueryInventoryFinishedListener mGotInventoryListener =
      new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
          if (helper == null) {
            notifyFinished();
            return;
          }

          if (result.isFailure()) {
            notifyFinished();
            return;
          }

          Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
          boolean subscribedToPremium = (premiumPurchase != null &&
              verifyDeveloperPayload(premiumPurchase));

          PremiumStatus.getInstance().setIsPremium(InAppPurchaseActivity.this, subscribedToPremium);

          notifyFinished();
        }
      };

  private boolean verifyDeveloperPayload(Purchase p) {
    String payload = p.getDeveloperPayload();
    String currentUserId = CurrentUserId.getInstance().getCurrentUserId();

    return payload != null && currentUserId != null && payload.equals(currentUserId);
  }

  private void notifyPurchaseFinished() {
    if (onInAppPurchaseFinishedListener != null) {
      onInAppPurchaseFinishedListener.inAppPurchasesFinished();
    }
  }

  protected void becomePremium(OnInAppPurchaseFinishedListener onInAppPurchaseFinishedListener) {
    this.onInAppPurchaseFinishedListener = onInAppPurchaseFinishedListener;

    initHelper();

    helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
      public void onIabSetupFinished(IabResult result) {
        if (!result.isSuccess()) {
          showToastMessage(
              getString(R.string.activity_in_app_purchase_problem_setting_app));
          notifyFinished();
          return;
        }

        if (helper == null) {
          notifyFinished();
          return;
        }

        if (!helper.subscriptionsSupported()) {
          showToastMessage(getString(R.string.activity_in_app_purchase_subscription_not_supported));
          notifyPurchaseFinished();
          return;
        }

        String payload = CurrentUserId.getInstance().getCurrentUserId();

        helper.launchPurchaseFlow(InAppPurchaseActivity.this,
            SKU_PREMIUM, IabHelper.ITEM_TYPE_SUBS,
            RC_REQUEST, mPurchaseFinishedListener, payload);
      }
    });
  }

  private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener =
      new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
          if (helper == null) {
            notifyPurchaseFinished();
            return;
          }

          if (result.isFailure()) {
            showToastMessage(getString(R.string.activity_in_app_purchase_unknown_error_purchasing));
            notifyPurchaseFinished();
            return;
          }

          if (!verifyDeveloperPayload(purchase)) {
            showToastMessage(getString(R.string.activity_in_app_purchase_auth_error_purchasing));
            notifyPurchaseFinished();
            return;
          }

          if (purchase.getSku().equals(SKU_PREMIUM)) {
            showToastMessage(getString(R.string.activity_in_app_purchase_success_becoming_premium));
            PremiumStatus.getInstance().setIsPremium(InAppPurchaseActivity.this, true);
          }

          notifyPurchaseFinished();
        }
      };
}
