package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportBaseModel;

public class BaseReportLayoutView extends LinearLayout {

  @InjectView(R.id.tv_income) TextView tv_income;
  @InjectView(R.id.tv_expense) TextView tv_expense;
  @InjectView(R.id.tv_balance) TextView tv_balance;

  public BaseReportLayoutView(Context context) {
    super(context);
    init();
  }

  public BaseReportLayoutView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BaseReportLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_base_report_layout, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);
  }

  public void setMonthReportObject(ReportBaseModel reportBaseModel) {
    double income = reportBaseModel.getIncome();
    double spent = reportBaseModel.getSpent();
    double balance = income - spent;

    tv_income.setText(
        String.format(getContext().getString(R.string.view_base_report_layout_amount_text_template),
            income));
    tv_expense.setText(
        String.format(getContext().getString(R.string.view_base_report_layout_amount_text_template),
            spent));
    tv_balance.setText(
        String.format(getContext().getString(R.string.view_base_report_layout_amount_text_template),
            balance));
  }
}
