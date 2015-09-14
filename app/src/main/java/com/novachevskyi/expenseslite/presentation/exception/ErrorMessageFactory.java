package com.novachevskyi.expenseslite.presentation.exception;

import android.content.Context;
import com.novachevskyi.expenseslite.R;
import retrofit.RetrofitError;

public class ErrorMessageFactory {

  private static final int NOT_FOUND_STATUS_CODE = 404;

  public static String create(Context context, Exception exception) {
    String message = context.getString(R.string.exception_message_generic);

    if (exception instanceof RetrofitError) {
      RetrofitError error = (RetrofitError) exception;
      if (error.getResponse() != null && error.getResponse().getStatus() == NOT_FOUND_STATUS_CODE) {
        message = context.getString(R.string.exception_message_not_found_web_data_error);
      } else {
        message = context.getString(R.string.exception_message_loading_web_data_error);
      }
    }

    return message;
  }
}
