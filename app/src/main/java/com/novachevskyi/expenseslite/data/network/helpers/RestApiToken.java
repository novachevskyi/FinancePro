package com.novachevskyi.expenseslite.data.network.helpers;

import android.content.SharedPreferences;
import android.text.TextUtils;

public class RestApiToken {

  private static final String REST_API_TOKEN_KEY = "rest_api_token";

  private static RestApiToken instance;
  private String token;
  private SharedPreferences sharedPreferences;

  public static RestApiToken getInstance() {
    if (instance == null) {
      synchronized (RestApiToken.class) {
        if (instance == null) {
          instance = new RestApiToken();
        }
      }
    }
    return instance;
  }

  public void setSharedPreferences(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public String getToken() {
    String token = this.token;

    if (TextUtils.isEmpty(token)) {
      if (sharedPreferences != null) {
        token = sharedPreferences.getString(REST_API_TOKEN_KEY, "");
      }
    }

    return token;
  }

  public void setToken(String token) {
    this.token = token;

    if (sharedPreferences != null) {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString(REST_API_TOKEN_KEY, token);
      editor.apply();
    }
  }
}
