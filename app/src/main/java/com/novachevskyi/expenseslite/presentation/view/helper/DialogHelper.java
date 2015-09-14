package com.novachevskyi.expenseslite.presentation.view.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import com.novachevskyi.expenseslite.R;

public class DialogHelper {
  public static ProgressDialog constructProgressDialogWithSpinner(Activity activity, String title,
      String message) {
    ProgressDialog progressDialog = new ProgressDialog(activity);

    progressDialog.setTitle(title);
    progressDialog.setMessage(message);
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    return progressDialog;
  }

  public static AlertDialog constructAlertDialogWithOkButton(Activity activity, String title,
      String message, final OnDialogButtonClickedListener onDialogButtonClickedListener) {
    return new AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.dialog_ok_button_title, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            onDialogButtonClickedListener.onOkClick();
          }
        }).create();
  }

  public static AlertDialog constructAlertDialogWithYesNoButton(Activity activity, String title,
      String message, final OnDialogYesNoButtonClickedListener onDialogButtonClickedListener) {
    return new AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.dialog_yes_button_title, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            onDialogButtonClickedListener.onYesClick();
          }
        }).setNegativeButton(R.string.dialog_no_button_title,
            new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                onDialogButtonClickedListener.onNoClick();
              }
            }).create();
  }

  public interface OnDialogButtonClickedListener {
    void onOkClick();
  }

  public interface OnDialogYesNoButtonClickedListener {
    void onYesClick();

    void onNoClick();
  }
}