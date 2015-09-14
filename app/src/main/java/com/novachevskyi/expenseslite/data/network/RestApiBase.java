package com.novachevskyi.expenseslite.data.network;

import com.novachevskyi.expenseslite.data.network.helpers.RestApiToken;
import com.squareup.okhttp.OkHttpClient;
import java.util.concurrent.TimeUnit;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

public class RestApiBase {

  protected RestAdapter restAdapter;

  protected RestApiBase() {
    restAdapter = buildRestAdapter();
  }

  private RestAdapter buildRestAdapter() {
    return new RestAdapter.Builder()
        .setEndpoint(ApiConstants.BASE_URL)
        .setConverter(new JacksonConverter())
        .setClient(getHttpClient())
        .setRequestInterceptor(getRequestInterceptor())
        .build();
  }

  private RequestInterceptor getRequestInterceptor() {
    return new RequestInterceptor() {
      @Override
      public void intercept(RequestFacade request) {
        request.addHeader(ApiConstants.PARSE_APPLICATION_ID_HEADER,
            ApiConstants.getParseApplicationId());
        request.addHeader(ApiConstants.PARSE_REST_API_KEY_HEADER,
            ApiConstants.getParseRestApiKey());
        request.addHeader(ApiConstants.PARSE_SESSION_TOKEN_HEADER,
            RestApiToken.getInstance().getToken());
      }
    };
  }

  private Client getHttpClient() {
    OkHttpClient httpClient = new OkHttpClient();
    httpClient.setConnectTimeout(ApiConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
    httpClient.setReadTimeout(ApiConstants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
    return new OkClient(httpClient);
  }
}
