package com.novachevskyi.expenseslite.presentation.view.base;

import android.content.Context;

public interface LoadDataView {

  void showLoading();

  void hideLoading();

  void showRetry();

  void hideRetry();

  void showError(String message);

  Context getContext();

  void dataValidated();
}
