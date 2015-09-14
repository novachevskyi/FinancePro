package com.novachevskyi.expenseslite.presentation.utils;

import android.content.Context;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

  private final static int MIN_LENGTH = 1;
  private final static int MAX_LENGTH = 255;

  public static String validateLogin(Context context, String login) {
    String error = null;

    if (login.length() < MIN_LENGTH) {
      error = context.getString(R.string.validator_login_is_required);
    } else if (login.length() > MAX_LENGTH) {
      error = context.getString(R.string.validator_login_is_to_long);
    } else if (!login.matches("^[0-9a-zA-Z]*$")) {
      error = context.getString(R.string.validator_characters_not_allowed);
    }

    return error;
  }

  public static String validateTitle(Context context, String title) {
    String error = null;

    if (title.length() < MIN_LENGTH) {
      error = context.getString(R.string.validator_title_is_required);
    } else if (title.length() > MAX_LENGTH) {
      error = context.getString(R.string.validator_title_is_to_long);
    }

    return error;
  }

  public static String validateAmount(Context context, String amount) {
    String error = null;

    if (amount.length() < MIN_LENGTH) {
      error = context.getString(R.string.validator_amount_is_required);
    }

    return error;
  }

  public static String validateEmail(Context context, String email) {
    String error = null;

    if (email.length() < MIN_LENGTH) {
      error = context.getString(R.string.validator_email_is_required);
    } else if (email.length() > MAX_LENGTH) {
      error = context.getString(R.string.validator_email_is_too_long);
    } else if (!isEmailValid(email)) {
      error = context.getString(R.string.validator_email_is_not_valid);
    }

    return error;
  }

  private static boolean isEmailValid(String email) {
    if (email == null) {
      return false;
    }

    String regExp =
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);

    return matcher.matches();
  }

  public static String validatePassword(Context context, String password) {
    String error = null;

    if (!password.matches("^.{6,32}$")) {
      error = context.getString(R.string.validator_password_have_to_contain_characters);
    }

    return error;
  }

  public static String validatePasswordConfirm(Context context, String password, String confirm) {
    String error = null;

    if (!password.equals(confirm)) {
      error = context.getString(R.string.validator_password_confirm_should_match);
    }

    return error;
  }

  public static String validateAccountModel(Context context, AccountModel selectedAccountModel) {
    String error = null;

    if (selectedAccountModel == null) {
      error = context.getString(R.string.validator_account_is_required);
    }

    return error;
  }
}
