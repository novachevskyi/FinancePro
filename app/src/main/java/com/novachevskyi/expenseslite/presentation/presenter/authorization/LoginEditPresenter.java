package com.novachevskyi.expenseslite.presentation.presenter.authorization;

import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.utils.Validator;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoginEditView;

public abstract class LoginEditPresenter implements Presenter {

  private UserLoginEditView userLoginEditView;

  public LoginEditPresenter(UserLoginEditView userLoginEditView) {
    this.userLoginEditView = userLoginEditView;
  }

  public boolean checkLogin(String login) {
    String error = Validator.validateLogin(userLoginEditView.getContext(), login);

    if (error == null) {
      userLoginEditView.loginDataAccepted();
      return true;
    } else {
      userLoginEditView.loginDataError(error);
      return false;
    }
  }

  public boolean checkPassword(String password) {
    String error = Validator.validatePassword(userLoginEditView.getContext(), password);

    if (error == null) {
      userLoginEditView.passwordDataAccepted();
      return true;
    } else {
      userLoginEditView.passwordDataError(error);
      return false;
    }
  }
}
