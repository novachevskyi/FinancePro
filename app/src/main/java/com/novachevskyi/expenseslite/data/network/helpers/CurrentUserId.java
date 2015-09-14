package com.novachevskyi.expenseslite.data.network.helpers;

import android.content.SharedPreferences;
import android.text.TextUtils;

public class CurrentUserId {

  private static final String CURRENT_USER_ID_KEY = "current_user_id";

  private static CurrentUserId instance;
  private String currentUserId;
  private SharedPreferences sharedPreferences;

  public static CurrentUserId getInstance() {
    if (instance == null) {
      synchronized (CurrentUserId.class) {
        if (instance == null) {
          instance = new CurrentUserId();
        }
      }
    }
    return instance;
  }

  public void setSharedPreferences(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public String getCurrentUserId() {
    String userId = this.currentUserId;

    if (TextUtils.isEmpty(userId)) {
      if (sharedPreferences != null) {
        userId = sharedPreferences.getString(CURRENT_USER_ID_KEY, "");
      }
    }

    return userId;
  }

  public void setCurrentUserId(String userId) {
    this.currentUserId = userId;

    if (sharedPreferences != null) {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString(CURRENT_USER_ID_KEY, userId);
      editor.apply();
    }
  }
}
