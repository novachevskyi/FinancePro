package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;

public class BudgetReportLayoutView extends LinearLayout {

  @InjectView(R.id.tv_income) TextView tv_income;
  @InjectView(R.id.tv_expense) TextView tv_expense;
  @InjectView(R.id.tv_balance) TextView tv_balance;

  public BudgetReportLayoutView(Context context) {
    super(context);
    init();
  }

  public BudgetReportLayoutView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BudgetReportLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_budget_report_layout, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);
  }

  public void setMonthReportObject(double startBudget, double current) {
    double spent = startBudget - current;

    tv_income.setText(
        String.format(getContext().getString(R.string.view_base_report_layout_amount_text_template),
            startBudget));
    tv_expense.setText(
        String.format(getContext().getString(R.string.view_base_report_layout_amount_text_template),
            spent));
    tv_balance.setText(
        String.format(getContext().getString(R.string.view_base_report_layout_amount_text_template),
            current));
  }
}
