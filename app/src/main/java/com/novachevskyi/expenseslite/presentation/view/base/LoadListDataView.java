package com.novachevskyi.expenseslite.presentation.view.base;

public interface LoadListDataView extends LoadDataView {
  void showMainLayout();

  void hideMainLayout();

  void showEmptyLayout();

  void hideEmptyLayout();

  void showLargeProgress();

  void hideLargeProgress();

  void showSmallError(String message);
}
