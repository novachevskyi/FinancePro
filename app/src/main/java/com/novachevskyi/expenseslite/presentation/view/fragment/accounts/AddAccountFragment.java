package com.novachevskyi.expenseslite.presentation.view.fragment.accounts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTextChanged;
import com.google.android.gms.ads.InterstitialAd;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.presenter.accounts.AddAccountPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountLoadView;
import com.novachevskyi.expenseslite.presentation.view.component.AcceptView;
import com.novachevskyi.expenseslite.presentation.view.component.MoneyEditTextView;
import com.novachevskyi.expenseslite.presentation.view.component.PaymentMethodSpinnerView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseAdMobFragment;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;

public class AddAccountFragment extends BaseAdMobFragment implements AccountLoadView {

  private AddAccountPresenter addAccountPresenter;

  @InjectView(R.id.et_name) EditText et_name;
  @InjectView(R.id.v_money) MoneyEditTextView v_money;
  @InjectView(R.id.v_method) PaymentMethodSpinnerView v_method;

  @InjectView(R.id.ll_dummy) LinearLayout ll_dummy;

  @InjectView(R.id.v_accept) AcceptView v_accept;

  private ProgressDialog addAccountProgressDialog;
  private AlertDialog addAccountErrorAlertDialog;

  public static AddAccountFragment newInstance() {
    AddAccountFragment addAccountFragment = new AddAccountFragment();

    Bundle argumentsBundle = new Bundle();
    addAccountFragment.setArguments(argumentsBundle);

    return addAccountFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    hideKeyboard();

    initialize();
  }

  private void initialize() {
    addAccountProgressDialog = DialogHelper.constructProgressDialogWithSpinner(getActivity(),
        getContext().getString(R.string.fragment_add_account_creating_fragment),
        getContext().getString(R.string.fragment_add_account_please_wait_for_server_response));
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_add_account, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeAcceptView();
    initializeAd();

    return fragmentView;
  }

  private void initializeAd() {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      constructAddAccountInterstitialAd(new BaseAdMobFragment.OnAdInterstitialLoadListener() {
        @Override public void onLoad(InterstitialAd adView) {
          if (adView.isLoaded()) {
            adView.show();
          }
        }
      });
    }
  }

  private void initializeAcceptView() {
    v_accept.setOnAcceptClickListener(new AcceptView.OnAcceptClickListener() {
      @Override public void onAccept() {
        tryVibrate();
        AddAccountFragment.this.validateData();
      }
    });
  }

  private void validateData() {
    if (addAccountPresenter != null) {
      if (et_name != null) {
        addAccountPresenter.validateData(et_name.getText().toString());
      }
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    this.addAccountPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.addAccountPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.addAccountPresenter =
        new AddAccountPresenter(this);
  }

  @Override public void showError(String message) {
    addAccountErrorAlertDialog = DialogHelper.constructAlertDialogWithOkButton(getActivity(),
        getContext().getString(R.string.fragment_add_account_can_not_create_new_account), message,
        new DialogHelper.OnDialogButtonClickedListener() {
          @Override public void onOkClick() {
            addAccountErrorAlertDialog.cancel();
          }
        });

    addAccountErrorAlertDialog.show();
  }

  @Override public void dataValidated() {
    AddAccountFragment.this.createAccount();
  }

  @Override public void showLoading() {
    addAccountProgressDialog.show();
  }

  @Override public void hideLoading() {
    addAccountProgressDialog.cancel();
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
  }

  private void createAccount() {
    if (addAccountPresenter != null) {
      if (et_name != null
          && v_money != null
          && v_method != null) {
        if (et_name.getError() == null) {
          addAccountPresenter.initialize(et_name.getText().toString(),
              v_money.getValue(), v_method.getSelectedAccountType());
        }
      }
    }
  }

  @OnTextChanged(R.id.et_name) void onTitleTextChanged() {
    AddAccountFragment.this.checkName();
  }

  private void checkName() {
    if (addAccountPresenter != null) {
      if (et_name != null) {
        addAccountPresenter.checkName(et_name.getText().toString());
      }
    }
  }

  @Override public void accountLoaded(AccountModel account) {
    showToastMessage(
        getContext().getString(R.string.fragment_add_account_account_created_successfully));

    if (onAccountCreatedListener != null) {
      onAccountCreatedListener.onAccountCreated(account);
    }
  }

  private OnAccountCreatedListener onAccountCreatedListener;

  public void setOnAccountCreatedListener(
      OnAccountCreatedListener onAccountCreatedListener) {
    this.onAccountCreatedListener = onAccountCreatedListener;
  }

  public interface OnAccountCreatedListener {
    void onAccountCreated(AccountModel accountModel);
  }

  @Override public void nameDataError(String message) {
    et_name.setError(message);
    showToastMessage(message);
  }

  @Override public void nameDataAccepted() {
    et_name.setError(null);
  }
}
