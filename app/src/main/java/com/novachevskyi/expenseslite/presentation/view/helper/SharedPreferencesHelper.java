package com.novachevskyi.expenseslite.presentation.view.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

  private static final String FINANCE_PRO_UI_PRIVATE_SHARED_PREFERENCES_KEY =
      "finance_pro_ui_private_shared_preferences";

  public static int getInt(Context context, String key, int defValue) {
    return getSharedPreferences(context).getInt(key, defValue);
  }

  public static void setInt(Context context, String key, int value) {
    SharedPreferences.Editor editor = getSharedPreferences(context).edit();
    editor.putInt(key, value);
    editor.apply();
  }

  public static boolean getBoolean(Context context, String key, boolean defValue) {
    return getSharedPreferences(context).getBoolean(key, defValue);
  }

  public static void setBoolean(Context context, String key, boolean value) {
    SharedPreferences.Editor editor = getSharedPreferences(context).edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  private static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(FINANCE_PRO_UI_PRIVATE_SHARED_PREFERENCES_KEY,
        Context.MODE_PRIVATE);
  }
}
