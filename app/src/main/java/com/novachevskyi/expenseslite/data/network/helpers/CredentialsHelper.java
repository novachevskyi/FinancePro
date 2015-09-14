package com.novachevskyi.expenseslite.data.network.helpers;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.novachevskyi.expenseslite.data.models.users.UserEntity;

public class CredentialsHelper {
  public static void setCurrentUserCredentials(UserEntity userEntity) {
    if (userEntity != null) {
      if (!TextUtils.isEmpty(userEntity.sessionToken)) {
        RestApiToken.getInstance().setToken(userEntity.sessionToken);
      }

      if (!TextUtils.isEmpty(userEntity.objectId)) {
        CurrentUserId.getInstance().setCurrentUserId(userEntity.objectId);
      }
    }
  }

  public static void logout() {
    RestApiToken.getInstance().setToken(null);
    CurrentUserId.getInstance().setCurrentUserId(null);
  }

  public static void setCurrentSharedPreferences(SharedPreferences sharedPreferences) {
    RestApiToken.getInstance().setSharedPreferences(sharedPreferences);
    CurrentUserId.getInstance().setSharedPreferences(sharedPreferences);
  }
}
