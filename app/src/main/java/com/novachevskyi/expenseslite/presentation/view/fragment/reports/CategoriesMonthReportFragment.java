package com.novachevskyi.expenseslite.presentation.view.fragment.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportWithCategoriesModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import com.novachevskyi.expenseslite.presentation.presenter.reports.ReportsCategoriesMonthPresenter;
import com.novachevskyi.expenseslite.presentation.view.base.reports.ReportsCategoriesMonthLoadView;
import com.novachevskyi.expenseslite.presentation.view.component.CategoriesChartReportLayoutView;
import com.novachevskyi.expenseslite.presentation.view.component.ExpensesSwitchButtonView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseFragment;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CategoriesMonthReportFragment extends BaseFragment implements
    ReportsCategoriesMonthLoadView {

  private ReportsCategoriesMonthPresenter reportsCategoriesMonthPresenter;

  @InjectView(R.id.ll_main) LinearLayout ll_main;
  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;
  @InjectView(R.id.rl_retry) RelativeLayout rl_retry;

  @InjectView(R.id.tv_title) TextView tv_title;

  @InjectView(R.id.v_categories) CategoriesChartReportLayoutView v_categories;

  @InjectView(R.id.v_expense_switch) ExpensesSwitchButtonView v_expense_switch;

  @InjectView(R.id.tv_total_title) TextView tv_total_title;
  @InjectView(R.id.tv_total) TextView tv_total;

  private ReportWithCategoriesModel reportWithCategoriesModel;

  public static CategoriesMonthReportFragment newInstance() {
    CategoriesMonthReportFragment reportFragment = new CategoriesMonthReportFragment();

    Bundle argumentsBundle = new Bundle();
    reportFragment.setArguments(argumentsBundle);

    return reportFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initialize();
  }

  private void initialize() {

  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView =
        inflater.inflate(R.layout.fragment_categories_month_report, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeRetry();
    initializeSwitchButton();
    initializeTitle();

    return fragmentView;
  }

  private void initializeTitle() {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(
        getContext().getString(R.string.fragment_categories_month_report_date_format));
    String formattedDate = formatter.print(new LocalDate());
    tv_title.setText(String.format(
        getContext().getString(R.string.fragment_categories_month_report_report_by_categories),
        formattedDate));
  }

  private void initializeSwitchButton() {
    v_expense_switch.setOnSwitchClickListener(new ExpensesSwitchButtonView.OnSwitchClickListener() {
      @Override public void onSwitched(TransactionType transactionType) {
        switchViews(transactionType);
      }
    });
  }

  private void switchViews(TransactionType transactionType) {
    switch (transactionType) {
      case INCOME:
        if (v_categories != null) {
          v_categories.displayIncomes();
        }
        setTotalIncome();
        break;
      case OUTCOME:
        if (v_categories != null) {
          v_categories.displayExpenses();
        }
        setTotalExpense();
        break;
    }
  }

  private void setTotalExpense() {
    if (reportWithCategoriesModel != null) {
      tv_total_title.setText(
          getContext().getString(R.string.fragment_categories_month_report_total_expenses));
      tv_total.setText(String.format(
          getContext().getString(R.string.fragment_categories_month_report_amount_text_template),
          this.reportWithCategoriesModel.getSpent()));
    }
  }

  private void setTotalIncome() {
    if (reportWithCategoriesModel != null) {
      tv_total_title.setText(
          getContext().getString(R.string.fragment_categories_month_report_total_incomes));
      tv_total.setText(String.format(
          getContext().getString(R.string.fragment_categories_month_report_amount_text_template),
          this.reportWithCategoriesModel.getIncome()));
    }
  }

  private void initializeRetry() {
    rl_retry.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        CategoriesMonthReportFragment.this.reloadData();
      }
    });
  }

  private void reloadData() {
    this.reportsCategoriesMonthPresenter.initialize();
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.reportsCategoriesMonthPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.reportsCategoriesMonthPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.reportsCategoriesMonthPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.reportsCategoriesMonthPresenter =
        new ReportsCategoriesMonthPresenter(this);
  }

  @Override public void showError(String message) {

  }

  @Override public void dataValidated() {

  }

  @Override public void showLoading() {
    rl_progress.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    rl_progress.setVisibility(View.GONE);
  }

  @Override public void showRetry() {
    rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    rl_retry.setVisibility(View.GONE);
  }

  @Override public void categoriesMonthReportLoaded(
      ReportWithCategoriesModel reportWithCategoriesModel) {
    if (reportWithCategoriesModel != null) {
      setThisMonthReport(reportWithCategoriesModel);
    }
  }

  private void setThisMonthReport(ReportWithCategoriesModel reportWithCategoriesModel) {
    this.reportWithCategoriesModel = reportWithCategoriesModel;

    v_categories.setCategoriesReport(reportWithCategoriesModel);

    if (v_expense_switch != null) {
      switchViews(v_expense_switch.getSelectedType());
    }
  }

  @Override public void showMainView() {
    ll_main.setVisibility(View.VISIBLE);
  }

  @Override public void hideMainView() {
    ll_main.setVisibility(View.GONE);
  }
}
