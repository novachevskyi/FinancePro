package com.novachevskyi.expenseslite;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.novachevskyi.expenseslite.data.network.helpers.CredentialsHelper;
import com.novachevskyi.expenseslite.presentation.utils.AppConstants;
import net.danlew.android.joda.JodaTimeAndroid;

public class FinanceProApplication extends Application {

  private static final String FINANCE_PRO_PRIVATE_SHARED_PREFERENCES_KEY =
      "finance_pro_private_shared_preferences";

  @Override
  public void onCreate() {
    super.onCreate();
    Crashlytics.start(this);
    FlurryAgent.setLogEnabled(false);
    FlurryAgent.init(this, AppConstants.getFlurryAnalyticsAppKey());
    JodaTimeAndroid.init(this);

    initializeRestApiTokenSharedPreferences();
  }

  private void initializeRestApiTokenSharedPreferences() {
    CredentialsHelper.setCurrentSharedPreferences(
        getSharedPreferences(FINANCE_PRO_PRIVATE_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE));
  }
}
