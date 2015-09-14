package com.novachevskyi.expenseslite.data.network;

import android.text.TextUtils;

public class ApiConstants {
  public static final String BASE_URL = "https://api.parse.com";

  public static final int HTTP_CONNECT_TIMEOUT = 6000;
  public static final int HTTP_READ_TIMEOUT = 10000;

  public static final String WHERE_CLAUSE = "where";
  public static final String ORDER_CLAUSE = "order";

  public static final String FIELD_NAME = "name";
  public static final String FIELD_PAYMENT_DATE = "paymentDate";

  public static final String PARSE_APPLICATION_ID_HEADER = "X-Parse-Application-Id";
  public static final String PARSE_REST_API_KEY_HEADER = "X-Parse-REST-API-Key";
  public static final String PARSE_SESSION_TOKEN_HEADER = "X-Parse-Session-Token";

  private static final String PARSE_APPLICATION_ID = "";
  private static final String PARSE_REST_API_KEY = "";

  public static String getParseApplicationId() {
    if (TextUtils.isEmpty(PARSE_APPLICATION_ID)) {
      throw new IllegalArgumentException("You should define PARSE_APPLICATION_ID in ApiConstants.");
    }

    return PARSE_APPLICATION_ID;
  }

  public static String getParseRestApiKey() {
    if (TextUtils.isEmpty(PARSE_REST_API_KEY)) {
      throw new IllegalArgumentException("You should define PARSE_REST_API_KEY in ApiConstants.");
    }

    return PARSE_REST_API_KEY;
  }
}
