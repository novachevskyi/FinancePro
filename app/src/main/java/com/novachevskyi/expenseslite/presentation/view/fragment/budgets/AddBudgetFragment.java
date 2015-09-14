package com.novachevskyi.expenseslite.presentation.view.fragment.budgets;

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
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.presenter.budgets.AddBudgetPresenter;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.base.budgets.BudgetLoadView;
import com.novachevskyi.expenseslite.presentation.view.component.AcceptView;
import com.novachevskyi.expenseslite.presentation.view.component.CategorySpinnerView;
import com.novachevskyi.expenseslite.presentation.view.component.DatePickerTextViewView;
import com.novachevskyi.expenseslite.presentation.view.component.MoneyEditTextView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseAdMobFragment;
import com.novachevskyi.expenseslite.presentation.view.helper.DialogHelper;

public class AddBudgetFragment extends BaseAdMobFragment implements
    BudgetLoadView {

  private AddBudgetPresenter addBudgetPresenter;

  @InjectView(R.id.v_money) MoneyEditTextView v_money;
  @InjectView(R.id.v_alert_money) MoneyEditTextView v_alert_money;
  @InjectView(R.id.et_notes) EditText et_notes;
  @InjectView(R.id.v_category) CategorySpinnerView v_category;
  @InjectView(R.id.v_date_to_picker) DatePickerTextViewView v_date_to_picker;
  @InjectView(R.id.v_date_from_picker) DatePickerTextViewView v_date_from_picker;

  @InjectView(R.id.ll_dummy) LinearLayout ll_dummy;

  @InjectView(R.id.v_accept) AcceptView v_accept;

  private ProgressDialog addBudgetProgressDialog;
  private AlertDialog addBudgetErrorAlertDialog;

  public static AddBudgetFragment newInstance() {
    AddBudgetFragment addBudgetFragment =
        new AddBudgetFragment();

    Bundle argumentsBundle = new Bundle();
    addBudgetFragment.setArguments(argumentsBundle);

    return addBudgetFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    hideKeyboard();

    initialize();
  }

  private void initialize() {
    addBudgetProgressDialog =
        DialogHelper.constructProgressDialogWithSpinner(getActivity(),
            getContext().getString(R.string.fragment_add_budget_creating_fragment),
            getContext().getString(
                R.string.fragment_add_budget_please_wait_for_server_response));
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView =
        inflater.inflate(R.layout.fragment_add_budget, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeAcceptView();
    initializeDatePickers();
    initializeAd();

    return fragmentView;
  }

  private void initializeAd() {
    if (!PremiumStatus.getInstance().getIsPremium(getContext())) {
      constructAddBudgetInterstitialAd(new OnAdInterstitialLoadListener() {
        @Override public void onLoad(InterstitialAd adView) {
          if (adView.isLoaded()) {
            adView.show();
          }
        }
      });
    }
  }

  private void initializeDatePickers() {
    v_date_to_picker.setSupportFragmentManager(getFragmentManager());
    v_date_from_picker.setSupportFragmentManager(getFragmentManager());
  }

  private void initializeAcceptView() {
    v_accept.setOnAcceptClickListener(new AcceptView.OnAcceptClickListener() {
      @Override public void onAccept() {
        tryVibrate();
        AddBudgetFragment.this.validateData();
      }
    });
  }

  private void validateData() {
    if (addBudgetPresenter != null) {
      if (v_money != null) {
        Editable textValue = v_money.getText();
        if (textValue != null) {
          this.addBudgetPresenter.validateData(textValue.toString());
        }
      }
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    this.addBudgetPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.addBudgetPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.addBudgetPresenter =
        new AddBudgetPresenter(this);
  }

  @Override public void showError(String message) {
    addBudgetErrorAlertDialog =
        DialogHelper.constructAlertDialogWithOkButton(getActivity(),
            getContext().getString(
                R.string.fragment_add_budget_can_not_create_new_budget),
            message,
            new DialogHelper.OnDialogButtonClickedListener() {
              @Override public void onOkClick() {
                addBudgetErrorAlertDialog.cancel();
              }
            });

    addBudgetErrorAlertDialog.show();
  }

  @Override public void dataValidated() {
    AddBudgetFragment.this.createBudget();
  }

  @Override public void showLoading() {
    addBudgetProgressDialog.show();
  }

  @Override public void hideLoading() {
    addBudgetProgressDialog.cancel();
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
  }

  private void createBudget() {
    if (addBudgetPresenter != null) {
      if (v_money != null && v_alert_money != null
          && et_notes != null && v_category != null
          && v_date_to_picker != null && v_date_from_picker != null) {
        if (v_money.getError() == null) {
          addBudgetPresenter.initialize(v_category.getSelectedCategory().getNumericType(),
              v_date_from_picker.getSelectedDate(),
              v_date_to_picker.getSelectedDate(), v_money.getValue(), v_alert_money.getValue(),
              et_notes.getText().toString());
        }
      }
    }
  }

  private OnBudgetCreatedListener onBudgetCreatedListener;

  public void setOnBudgetCreatedListener(
      OnBudgetCreatedListener onBudgetCreatedListener) {
    this.onBudgetCreatedListener = onBudgetCreatedListener;
  }

  @Override public void budgetLoaded(BudgetModel source) {
    showToastMessage(
        getContext().getString(
            R.string.fragment_add_budget_budget_created_successfully));

    if (onBudgetCreatedListener != null) {
      onBudgetCreatedListener.onBudgetCreated(source);
    }
  }

  @Override public void amountDataError(String message) {
    v_money.setError(message);
    showToastMessage(message);
  }

  @Override public void amountDataAccepted() {
    v_money.setError(null);
  }

  public interface OnBudgetCreatedListener {
    void onBudgetCreated(BudgetModel budgetModel);
  }
}
