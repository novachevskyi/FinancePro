package com.novachevskyi.expenseslite.presentation.presenter.authorization;

import com.novachevskyi.expenseslite.presentation.utils.Validator;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserRegistrationEditView;

public abstract class RegistrationEditPresenter extends LoginEditPresenter {

  private UserRegistrationEditView userRegistrationEditView;

  public RegistrationEditPresenter(UserRegistrationEditView userRegistrationEditView) {
    super(userRegistrationEditView);

    this.userRegistrationEditView = userRegistrationEditView;
  }

  public boolean checkEmail(String email) {
    String error = Validator.validateEmail(userRegistrationEditView.getContext(), email);

    if (error == null) {
      userRegistrationEditView.emailDataAccepted();
      return true;
    } else {
      userRegistrationEditView.emailDataError(error);
      return false;
    }
  }

  public boolean checkPasswordConfirm(String password, String confirm) {
    String error = Validator.validatePasswordConfirm(userRegistrationEditView.getContext(),
        password, confirm);

    if (error == null) {
      userRegistrationEditView.passwordConfirmDataAccepted();
      return true;
    } else {
      userRegistrationEditView.passwordConfirmDataError(error);
      return false;
    }
  }
}
