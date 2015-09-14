package com.novachevskyi.expenseslite.presentation.view.base.authorization;

public interface UserRegistrationEditView extends UserLoginEditView {
  void emailDataError(String message);

  void emailDataAccepted();

  void passwordConfirmDataError(String message);

  void passwordConfirmDataAccepted();
}
