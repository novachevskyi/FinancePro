package com.novachevskyi.expenseslite.presentation.utils;

import android.text.TextUtils;

public class AppConstants {
  private static final String IN_APP_PURCHASES_PUBLIC_KEY = "";

  private static final String FLURRY_ANALYTICS_APP_KEY = "";

  public static String getInAppPurchasesPublicKey() {
    if (TextUtils.isEmpty(IN_APP_PURCHASES_PUBLIC_KEY)) {
      throw new IllegalArgumentException("You should define IN_APP_PURCHASES_PUBLIC_KEY in AppConstants.");
    }

    return IN_APP_PURCHASES_PUBLIC_KEY;
  }

  public static String getFlurryAnalyticsAppKey() {
    if (TextUtils.isEmpty(FLURRY_ANALYTICS_APP_KEY)) {
      throw new IllegalArgumentException("You should define FLURRY_ANALYTICS_APP_KEY in AppConstants.");
    }

    return FLURRY_ANALYTICS_APP_KEY;
  }
}
