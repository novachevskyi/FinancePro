package com.novachevskyi.expenseslite.presentation.view.fragment.transactions;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import com.novachevskyi.expenseslite.presentation.presenter.transactions.AddTransactionPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.base.transactions.TransactionLoadView;
import com.novachevskyi.expenseslite.presentation.view.component.AcceptView;
import com.novachevskyi.expenseslite.presentation.view.component.AccountPickerEditTextView;
import com.novachevskyi.expenseslite.presentation.view.component.CategorySpinnerView;
import com.novachevskyi.expenseslite.presentation.view.component.DatePickerTextViewView;
import com.novachevskyi.expenseslite.presentation.view.component.MoneyEditTextView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseAdMobFragment;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;

public class AddTransactionFragment extends BaseAdMobFragment implements TransactionLoadView {

  private static final String ADD_TRANSACTION_FRAGMENT_TYPE_KEY =
      "add_transaction_fragment_type_key";

  private TransactionType transactionType;

  private AddTransactionPresenter addTransactionPresenter;

  @InjectView(R.id.v_money) MoneyEditTextView v_money;
  @InjectView(R.id.v_date_picker) DatePickerTextViewView v_date_picker;
  @InjectView(R.id.v_account_picker) AccountPickerEditTextView v_account_picker;
  @InjectView(R.id.v_category) CategorySpinnerView v_category;
  @InjectView(R.id.et_notes) EditText et_notes;
  @InjectView(R.id.et_payee) EditText et_payee;
  @InjectView(R.id.cv_payee) CardView cv_payee;

  @InjectView(R.id.ll_dummy) LinearLayout ll_dummy;

  @InjectView(R.id.v_accept) AcceptView v_accept;

  private ProgressDialog addTransactionProgressDialog;
  private AlertDialog addTransactionErrorAlertDialog;

  public static AddTransactionFragment newInstance(int transactionType) {
    AddTransactionFragment addTransactionFragment = new AddTransactionFragment();

    Bundle argumentsBundle = new Bundle();
    argumentsBundle.putInt(ADD_TRANSACTION_FRAGMENT_TYPE_KEY, transactionType);
    addTransactionFragment.setArguments(argumentsBundle);

    return addTransactionFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    hideKeyboard();

    initialize();
  }

  private void initialize() {
    addTransactionProgressDialog = DialogHelper.constructProgressDialogWithSpinner(getActivity(),
        getContext().getString(R.string.fragment_add_transaction_creating_fragment),
        getContext().getString(R.string.fragment_add_transaction_please_wait_for_server_response));

    Bundle bundle = this.getArguments();
    int transactionTypeIntValue = bundle.getInt(ADD_TRANSACTION_FRAGMENT_TYPE_KEY, 0);
    transactionType = TransactionType.getTransactionType(transactionTypeIntValue);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeAcceptView();
    initializeDatePicker();
    initializeAccountPicker();
    initializePayeeLayout();
    initializeAd();

    return fragmentView;
  }

  private void initializeAd() {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      constructAddTransactionInterstitialAd(new BaseAdMobFragment.OnAdInterstitialLoadListener() {
        @Override public void onLoad(InterstitialAd adView) {
          if (adView.isLoaded()) {
            adView.show();
          }
        }
      });
    }
  }

  private void initializePayeeLayout() {
    if (transactionType == TransactionType.INCOME) {
      cv_payee.setVisibility(View.VISIBLE);
    } else {
      cv_payee.setVisibility(View.GONE);
    }
  }

  private void initializeAccountPicker() {
    v_account_picker.setSupportFragmentManager(getFragmentManager());
  }

  private void initializeDatePicker() {
    v_date_picker.setSupportFragmentManager(getFragmentManager());
  }

  private void initializeAcceptView() {
    v_accept.setOnAcceptClickListener(new AcceptView.OnAcceptClickListener() {
      @Override public void onAccept() {
        tryVibrate();
        AddTransactionFragment.this.validateData();
      }
    });
  }

  private void validateData() {
    if (addTransactionPresenter != null) {
      if (v_money != null && v_account_picker != null) {
        Editable textValue = v_money.getText();
        if (textValue != null) {
          this.addTransactionPresenter.validateData(textValue.toString(),
              v_account_picker.getSelectedAccountModel());
        }
      }
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    this.addTransactionPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.addTransactionPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.addTransactionPresenter =
        new AddTransactionPresenter(this);
  }

  @Override public void showError(String message) {
    addTransactionErrorAlertDialog = DialogHelper.constructAlertDialogWithOkButton(getActivity(),
        getContext().getString(R.string.fragment_add_transaction_can_not_create_new_transaction),
        message,
        new DialogHelper.OnDialogButtonClickedListener() {
          @Override public void onOkClick() {
            addTransactionErrorAlertDialog.cancel();
          }
        });

    addTransactionErrorAlertDialog.show();
  }

  @Override public void dataValidated() {
    AddTransactionFragment.this.createTransaction();
  }

  @Override public void showLoading() {
    addTransactionProgressDialog.show();
  }

  @Override public void hideLoading() {
    addTransactionProgressDialog.cancel();
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
  }

  private void createTransaction() {
    if (addTransactionPresenter != null) {
      if (v_money != null && v_account_picker != null
          && v_date_picker != null && et_notes != null && et_payee != null && v_category != null) {
        if (v_money.getError() == null && v_account_picker.getError() == null) {
          addTransactionPresenter.initialize(v_date_picker.getSelectedDate(),
              v_account_picker.getSelectedAccountModel().getAccountId(), v_money.getValue(),
              et_notes.getText().toString(), transactionType.getNumericType(),
              et_payee.getText().toString(), v_category.getSelectedCategory().getNumericType());
        }
      }
    }
  }

  @Override public void transactionLoaded(TransactionModel transactionModel) {
    showToastMessage(
        getContext().getString(R.string.fragment_add_transaction_transaction_created_successfully));

    if (onTransactionCreatedListener != null) {
      onTransactionCreatedListener.onTransactionCreated(transactionModel);
    }
  }

  private OnTransactionCreatedListener onTransactionCreatedListener;

  public void setOnTransactionCreatedListener(
      OnTransactionCreatedListener onTransactionCreatedListener) {
    this.onTransactionCreatedListener = onTransactionCreatedListener;
  }

  @Override public void amountDataError(String message) {
    v_money.setError(message);
    showToastMessage(message);
  }

  @Override public void amountDataAccepted() {
    v_money.setError(null);
  }

  @Override public void accountDataError(String message) {
    v_account_picker.setError(message);
    showToastMessage(message);
  }

  @Override public void accountDataAccepted() {
    v_account_picker.setError(null);
  }

  public interface OnTransactionCreatedListener {
    void onTransactionCreated(TransactionModel transactionModel);
  }
}
