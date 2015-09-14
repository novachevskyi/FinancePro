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
import com.novachevskyi.expenseslite.presentation.model.reports.GeneralReportModel;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportBaseModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType;
import com.novachevskyi.expenseslite.presentation.presenter.reports.ReportsGeneralPresenter;
import com.novachevskyi.expenseslite.presentation.view.base.reports.ReportsGeneralLoadView;
import com.novachevskyi.expenseslite.presentation.view.component.BaseChartReportLayoutView;
import com.novachevskyi.expenseslite.presentation.view.component.BaseReportLayoutView;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseFragment;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GeneralReportFragment extends BaseFragment implements ReportsGeneralLoadView {

  private ReportsGeneralPresenter reportsGeneralPresenter;

  @InjectView(R.id.ll_main) LinearLayout ll_main;
  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;
  @InjectView(R.id.rl_retry) RelativeLayout rl_retry;

  @InjectView(R.id.rl_today_details) RelativeLayout rl_today_details;
  @InjectView(R.id.tv_today_money) TextView tv_today_money;

  @InjectView(R.id.tv_month_money_title) TextView tv_month_money_title;
  @InjectView(R.id.rl_this_month_details) RelativeLayout rl_this_month_details;
  @InjectView(R.id.v_month_report) BaseReportLayoutView v_month_report;
  @InjectView(R.id.v_month_chart_report) BaseChartReportLayoutView v_month_chart_report;

  @InjectView(R.id.tv_current_money) TextView tv_current_money;

  public static GeneralReportFragment newInstance() {
    GeneralReportFragment reportFragment = new GeneralReportFragment();

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

    View fragmentView = inflater.inflate(R.layout.fragment_general_report, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeRetry();
    initializeTodayReportView();
    initializeThisMonthReportView();

    return fragmentView;
  }

  private void initializeThisMonthReportView() {
    rl_this_month_details.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getNavigator().navigateToTransactionsListScreen(getActivity(),
            TransactionFilterType.THIS_MONTH);
      }
    });
  }

  private void initializeTodayReportView() {
    rl_today_details.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getNavigator().navigateToTransactionsListScreen(getActivity(), TransactionFilterType.TODAY);
      }
    });
  }

  private void initializeRetry() {
    rl_retry.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        GeneralReportFragment.this.reloadData();
      }
    });
  }

  private void reloadData() {
    this.reportsGeneralPresenter.initialize();
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.reportsGeneralPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.reportsGeneralPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.reportsGeneralPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.reportsGeneralPresenter =
        new ReportsGeneralPresenter(this);
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

  @Override public void generalReportLoaded(GeneralReportModel generalReportModel) {
    if (generalReportModel != null) {
      setTodayReport(generalReportModel.getTodaySpentAmount());
      setThisMonthReport(generalReportModel.getThisMonthReport(),
          generalReportModel.getCurrentTotalAmount());
      setCurrentBalance(generalReportModel.getCurrentTotalAmount());
    }
  }

  private void setCurrentBalance(double currentTotalAmount) {
    tv_current_money.setText(
        String.format(getContext().getString(R.string.fragment_general_report_amount_text_template),
            currentTotalAmount));
  }

  private void setThisMonthReport(ReportBaseModel thisMonthReport, double currentTotalAmount) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(
        getContext().getString(R.string.fragment_general_report_date_format));
    String formattedDate = formatter.print(new LocalDate());
    tv_month_money_title.setText(String.format(
        getContext().getString(R.string.fragment_general_report_about_month_template),
        formattedDate));

    v_month_report.setMonthReportObject(thisMonthReport);
    v_month_chart_report.setMonthReportObject(thisMonthReport, currentTotalAmount);
  }

  private void setTodayReport(double todaySpentAmount) {
    tv_today_money.setText(
        String.format(getContext().getString(R.string.fragment_general_report_amount_text_template),
            todaySpentAmount));
  }

  @Override public void showMainView() {
    ll_main.setVisibility(View.VISIBLE);
  }

  @Override public void hideMainView() {
    ll_main.setVisibility(View.GONE);
  }
}
