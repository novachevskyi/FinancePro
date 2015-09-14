package com.novachevskyi.expenseslite.presentation.view.base.authorization;

import com.novachevskyi.expenseslite.presentation.view.base.EditView;

public interface UserLoginEditView extends EditView {
  void loginDataError(String message);

  void loginDataAccepted();

  void passwordDataError(String message);

  void passwordDataAccepted();
}
