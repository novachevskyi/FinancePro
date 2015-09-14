package com.novachevskyi.expenseslite.presentation.utils.purchases;

import android.content.Context;
import com.novachevskyi.expenseslite.presentation.view.helper.SharedPreferencesHelper;

public class PremiumStatus {

  private static final String PREMIUM_STATUS = "premium_status";

  private static PremiumStatus instance;
  private Boolean isPremium;

  public static PremiumStatus getInstance() {
    if (instance == null) {
      synchronized (PremiumStatus.class) {
        if (instance == null) {
          instance = new PremiumStatus();
        }
      }
    }
    return instance;
  }

  public Boolean getIsPremium(Context context) {
    Boolean isPremium = this.isPremium;

    if (isPremium == null) {
      isPremium = SharedPreferencesHelper.getBoolean(context, PREMIUM_STATUS, false);
    }

    return isPremium;
  }

  public void setIsPremium(Context context, boolean isPremium) {
    this.isPremium = isPremium;

    SharedPreferencesHelper.setBoolean(context, PREMIUM_STATUS, isPremium);
  }
}
