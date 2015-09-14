package com.novachevskyi.expenseslite.presentation.view.fragment.transactions;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.ads.InterstitialAd;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.presenter.transactions.AddTransactionTransferPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.base.transactions.TransactionTransferLoadView;
import com.novachevskyi.expenseslite.presentation.view.component.AcceptView;
import com.novachevskyi.expenseslite.presentation.view.component.AccountPickerEditTextView;
import com.novachevskyi.expenseslite.presentation.view.component.DatePickerTextViewView;
import com.novachevskyi.expenseslite.presentation.view.component.MoneyEditTextView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseAdMobFragment;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;

public class AddTransactionTransferFragment extends BaseAdMobFragment implements
    TransactionTransferLoadView {

  private AddTransactionTransferPresenter addTransactionTransferPresenter;

  @InjectView(R.id.v_money) MoneyEditTextView v_money;
  @InjectView(R.id.v_date_picker) DatePickerTextViewView v_date_picker;
  @InjectView(R.id.v_source_account_picker) AccountPickerEditTextView v_source_account_picker;
  @InjectView(R.id.v_destination_account_picker) AccountPickerEditTextView
      v_destination_account_picker;
  @InjectView(R.id.et_notes) EditText et_notes;

  @InjectView(R.id.ll_dummy) LinearLayout ll_dummy;

  @InjectView(R.id.v_accept) AcceptView v_accept;

  private ProgressDialog addTransactionTransferProgressDialog;
  private AlertDialog addTransactionTransferErrorAlertDialog;

  public static AddTransactionTransferFragment newInstance() {
    AddTransactionTransferFragment addTransactionTransferFragment =
        new AddTransactionTransferFragment();

    Bundle argumentsBundle = new Bundle();
    addTransactionTransferFragment.setArguments(argumentsBundle);

    return addTransactionTransferFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    hideKeyboard();

    initialize();
  }

  private void initialize() {
    addTransactionTransferProgressDialog =
        DialogHelper.constructProgressDialogWithSpinner(getActivity(),
            getContext().getString(R.string.fragment_add_transaction_transfer_creating_fragment),
            getContext().getString(
                R.string.fragment_add_transaction_transfer_please_wait_for_server_response));
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView =
        inflater.inflate(R.layout.fragment_add_transaction_transfer, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeAcceptView();
    initializeDatePicker();
    initializeAccountPicker();
    initializeAd();

    return fragmentView;
  }

  private void initializeAd() {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      constructAddTransferInterstitialAd(new BaseAdMobFragment.OnAdInterstitialLoadListener() {
        @Override public void onLoad(InterstitialAd adView) {
          if (adView.isLoaded()) {
            adView.show();
          }
        }
      });
    }
  }

  private void initializeAccountPicker() {
    v_source_account_picker.setSupportFragmentManager(getFragmentManager());
    v_destination_account_picker.setSupportFragmentManager(getFragmentManager());
  }

  private void initializeDatePicker() {
    v_date_picker.setSupportFragmentManager(getFragmentManager());
  }

  private void initializeAcceptView() {
    v_accept.setOnAcceptClickListener(new AcceptView.OnAcceptClickListener() {
      @Override public void onAccept() {
        tryVibrate();
        AddTransactionTransferFragment.this.validateData();
      }
    });
  }

  private void validateData() {
    if (addTransactionTransferPresenter != null) {
      if (v_money != null
          && v_source_account_picker != null
          && v_destination_account_picker != null) {
        Editable textValue = v_money.getText();
        if (textValue != null) {
          this.addTransactionTransferPresenter.validateData(textValue.toString(),
              v_source_account_picker.getSelectedAccountModel(),
              v_destination_account_picker.getSelectedAccountModel());
        } else {
          ll_dummy.requestFocus();
        }
      }
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    this.addTransactionTransferPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.addTransactionTransferPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.addTransactionTransferPresenter =
        new AddTransactionTransferPresenter(this);
  }

  @Override public void showError(String message) {
    addTransactionTransferErrorAlertDialog =
        DialogHelper.constructAlertDialogWithOkButton(getActivity(),
            getContext().getString(
                R.string.fragment_add_transaction_transfer_can_not_create_new_transaction),
            message,
            new DialogHelper.OnDialogButtonClickedListener() {
              @Override public void onOkClick() {
                addTransactionTransferErrorAlertDialog.cancel();
              }
            });

    addTransactionTransferErrorAlertDialog.show();
  }

  @Override public void dataValidated() {
    AddTransactionTransferFragment.this.createTransaction();
  }

  @Override public void showLoading() {
    addTransactionTransferProgressDialog.show();
  }

  @Override public void hideLoading() {
    addTransactionTransferProgressDialog.cancel();
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
  }

  private void createTransaction() {
    if (addTransactionTransferPresenter != null) {
      if (v_money != null && v_source_account_picker != null
          && v_date_picker != null && et_notes != null && v_destination_account_picker != null) {
        if (v_money.getError() == null && v_source_account_picker.getError() == null
            && v_destination_account_picker.getError() == null) {
          addTransactionTransferPresenter.initialize(v_date_picker.getSelectedDate(),
              v_source_account_picker.getSelectedAccountModel().getAccountId(),
              v_destination_account_picker.getSelectedAccountModel().getAccountId(),
              v_money.getValue(),
              et_notes.getText().toString(),
              v_source_account_picker.getSelectedAccountModel().getName(),
              v_destination_account_picker.getSelectedAccountModel().getName());
        }
      }
    }
  }

  private OnTransactionCreatedListener onTransactionCreatedListener;

  public void setOnTransactionCreatedListener(
      OnTransactionCreatedListener onTransactionCreatedListener) {
    this.onTransactionCreatedListener = onTransactionCreatedListener;
  }

  @Override public void transactionsLoaded(TransactionModel source, TransactionModel destination) {
    showToastMessage(
        getContext().getString(
            R.string.fragment_add_transaction_transfer_transaction_created_successfully));

    if (onTransactionCreatedListener != null) {
      onTransactionCreatedListener.onTransactionCreated(source, destination);
    }
  }

  @Override public void amountDataError(String message) {
    v_money.setError(message);
    showToastMessage(message);
  }

  @Override public void amountDataAccepted() {
    v_money.setError(null);
  }

  @Override public void sourceAccountDataError(String message) {
    v_source_account_picker.setError(message);
    showToastMessage(message);
  }

  @Override public void sourceAccountDataAccepted() {
    v_source_account_picker.setError(null);
  }

  @Override public void destinationAccountDataError(String message) {
    v_destination_account_picker.setError(message);
    showToastMessage(message);
  }

  @Override public void destinationAccountDataAccepted() {
    v_destination_account_picker.setError(null);
  }

  public interface OnTransactionCreatedListener {
    void onTransactionCreated(TransactionModel sourceTransactionModel,
        TransactionModel destinationTransactionModel);
  }
}
