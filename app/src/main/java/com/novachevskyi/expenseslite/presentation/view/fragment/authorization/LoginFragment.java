package com.novachevskyi.expenseslite.presentation.view.fragment.authorization;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.users.UserModel;
import com.novachevskyi.expenseslite.presentation.presenter.authorization.LoginPresenter;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoadView;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoginEditView;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;

public class LoginFragment extends UserRequestBaseFragment implements UserLoadView,
    UserLoginEditView {

  private LoginPresenter loginPresenter;

  private ProgressDialog loginProgressDialog;
  private AlertDialog loginErrorAlertDialog;

  @InjectView(R.id.et_username) EditText et_username;
  @InjectView(R.id.et_password) EditText et_password;

  public static LoginFragment newInstance() {
    LoginFragment splashFragment = new LoginFragment();

    Bundle argumentsBundle = new Bundle();
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initialize();
  }

  private void initialize() {
    loginProgressDialog = DialogHelper.constructProgressDialogWithSpinner(getActivity(),
        getContext().getString(
            R.string.fragment_login_authenticating),
        getContext().getString(R.string.fragment_login_please_wait_for_server_response));
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    this.loginPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.loginPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.loginPresenter =
        new LoginPresenter(this, this);
  }

  @Override public void userLoaded(UserModel user) {
    notifyUserLoadSucceed();
  }

  @Override public void showError(String message) {
    loginErrorAlertDialog = DialogHelper.constructAlertDialogWithOkButton(getActivity(),
        getContext().getString(R.string.fragment_login_authentication_error), message,
        new DialogHelper.OnDialogButtonClickedListener() {
          @Override public void onOkClick() {
            loginErrorAlertDialog.cancel();
          }
        });

    loginErrorAlertDialog.show();
  }

  @Override public void dataValidated() {
    LoginFragment.this.login();
  }

  @Override public void showLoading() {
    loginProgressDialog.show();
  }

  @Override public void hideLoading() {
    loginProgressDialog.cancel();
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
  }

  @OnClick(R.id.tv_sign_up) void onButtonSignUpClick() {
    LoginFragment.this.notifyRegistration();
  }

  private OnRegistrationRequestListener onRegistrationRequestListener;

  public void setOnRegistrationRequestListener(
      OnRegistrationRequestListener onRegistrationRequestListener) {
    this.onRegistrationRequestListener = onRegistrationRequestListener;
  }

  public interface OnRegistrationRequestListener {
    void onRegistration();
  }

  protected void notifyRegistration() {
    if (onRegistrationRequestListener != null) {
      onRegistrationRequestListener.onRegistration();
    }
  }

  @OnClick(R.id.btn_login) void onButtonLoginClick() {
    LoginFragment.this.validateData();
  }

  private void validateData() {
    if (loginPresenter != null) {
      if (et_username != null && et_password != null) {
        loginPresenter.validateData(et_username.getText().toString(),
            et_password.getText().toString());
      }
    }
  }

  @OnTextChanged(R.id.et_username) void onLoginTextChanged() {
    LoginFragment.this.checkLogin();
  }

  private void checkLogin() {
    if (loginPresenter != null) {
      if (et_username != null) {
        loginPresenter.checkLogin(et_username.getText().toString());
      }
    }
  }

  @OnTextChanged(R.id.et_password) void onPasswordTextChanged() {
    LoginFragment.this.checkPassword();
  }

  private void checkPassword() {
    if (loginPresenter != null) {
      if (et_password != null) {
        loginPresenter.checkPassword(et_password.getText().toString());
      }
    }
  }

  private void login() {
    if (loginPresenter != null) {
      if (et_username != null && et_password != null) {
        if (et_username.getError() == null && et_password.getError() == null) {
          loginPresenter.initialize(et_username.getText().toString(),
              et_password.getText().toString());
        }
      }
    }
  }

  @Override public void loginDataError(String message) {
    et_username.setError(message);
  }

  @Override public void loginDataAccepted() {
    et_username.setError(null);
  }

  @Override public void passwordDataError(String message) {
    et_password.setError(message);
  }

  @Override public void passwordDataAccepted() {
    et_password.setError(null);
  }
}
