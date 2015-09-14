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
import com.novachevskyi.expenseslite.presentation.presenter.authorization.RegistrationPresenter;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoadView;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserRegistrationEditView;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;

public class RegistrationFragment extends UserRequestBaseFragment implements UserLoadView,
    UserRegistrationEditView {

  private RegistrationPresenter registrationPresenter;

  private ProgressDialog signUpProgressDialog;
  private AlertDialog signUpErrorAlertDialog;

  @InjectView(R.id.et_username) EditText et_username;
  @InjectView(R.id.et_password) EditText et_password;
  @InjectView(R.id.et_email) EditText et_email;
  @InjectView(R.id.et_password_confirm) EditText et_password_confirm;

  public static RegistrationFragment newInstance() {
    RegistrationFragment registrationFragment = new RegistrationFragment();

    Bundle argumentsBundle = new Bundle();
    registrationFragment.setArguments(argumentsBundle);

    return registrationFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initialize();
  }

  private void initialize() {
    signUpProgressDialog = DialogHelper.constructProgressDialogWithSpinner(getActivity(),
        getContext().getString(R.string.fragment_registration_registration_title),
        getContext().getString(R.string.fragment_registration_please_wait_for_server_response));
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_registration, container, false);
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    this.registrationPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.registrationPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.registrationPresenter =
        new RegistrationPresenter(this, this);
  }

  @Override public void userLoaded(UserModel user) {
    notifyUserLoadSucceed();
  }

  @Override public void showError(String message) {
    signUpErrorAlertDialog = DialogHelper.constructAlertDialogWithOkButton(getActivity(),
        getContext().getString(R.string.fragment_registration_registration_error_title), message,
        new DialogHelper.OnDialogButtonClickedListener() {
          @Override public void onOkClick() {
            signUpErrorAlertDialog.cancel();
          }
        });

    signUpErrorAlertDialog.show();
  }

  @Override public void dataValidated() {
    RegistrationFragment.this.register();
  }

  @Override public void showLoading() {
    signUpProgressDialog.show();
  }

  @Override public void hideLoading() {
    signUpProgressDialog.cancel();
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
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

  @Override public void emailDataError(String message) {
    et_email.setError(message);
  }

  @Override public void emailDataAccepted() {
    et_email.setError(null);
  }

  @Override public void passwordConfirmDataError(String message) {
    et_password_confirm.setError(message);
  }

  @Override public void passwordConfirmDataAccepted() {
    et_password_confirm.setError(null);
  }

  @OnClick(R.id.btn_register) void onButtonRegistrationClick() {
    RegistrationFragment.this.validateData();
  }

  private void validateData() {
    if (registrationPresenter != null) {
      if (et_username != null
          && et_password != null
          && et_email != null
          && et_password_confirm != null) {
        registrationPresenter.validateData(et_username.getText().toString(),
            et_password.getText().toString(), et_password_confirm.getText().toString(),
            et_email.getText().toString());
      }
    }
  }

  private void register() {
    if (registrationPresenter != null) {
      if (et_username != null
          && et_password != null
          && et_email != null
          && et_password_confirm != null) {
        if (et_username.getError() == null
            && et_password.getError() == null
            && et_email.getError() == null
            && et_password_confirm.getError() == null) {
          registrationPresenter.initialize(et_username.getText().toString(),
              et_password.getText().toString(), et_email.getText().toString());
        }
      }
    }
  }

  @OnTextChanged(R.id.et_username) void onLoginTextChanged() {
    RegistrationFragment.this.checkLogin();
  }

  private void checkLogin() {
    if (registrationPresenter != null) {
      if (et_username != null) {
        registrationPresenter.checkLogin(et_username.getText().toString());
      }
    }
  }

  @OnTextChanged(R.id.et_password) void onPasswordTextChanged() {
    RegistrationFragment.this.checkPassword();
    RegistrationFragment.this.checkPasswordConfirm();
  }

  private void checkPassword() {
    if (registrationPresenter != null) {
      if (et_password != null) {
        registrationPresenter.checkPassword(et_password.getText().toString());
      }
    }
  }

  @OnTextChanged(R.id.et_email) void onEmailTextChanged() {
    RegistrationFragment.this.checkEmail();
  }

  private void checkEmail() {
    if (registrationPresenter != null) {
      if (et_email != null) {
        registrationPresenter.checkEmail(et_email.getText().toString());
      }
    }
  }

  @OnTextChanged(R.id.et_password_confirm) void onPasswordConfirmTextChanged() {
    RegistrationFragment.this.checkPasswordConfirm();
  }

  private void checkPasswordConfirm() {
    if (registrationPresenter != null) {
      if (et_password_confirm != null && et_password != null) {
        registrationPresenter.checkPasswordConfirm(et_password_confirm.getText().toString(),
            et_password.getText().toString());
      }
    }
  }
}
