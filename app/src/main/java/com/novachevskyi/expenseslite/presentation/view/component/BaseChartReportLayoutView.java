package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportBaseModel;
import java.math.BigDecimal;
import org.eazegraph.lib.charts.VerticalBarChart;
import org.eazegraph.lib.models.BarModel;

public class BaseChartReportLayoutView extends LinearLayout {

  private final static float TOTAL_PERCENTAGE_VALUE = 100.0f;
  private final static int DECIMAL_NUMBER = 2;

  @InjectView(R.id.v_bar_chart) VerticalBarChart v_bar_chart;

  public BaseChartReportLayoutView(Context context) {
    super(context);
    init();
  }

  public BaseChartReportLayoutView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BaseChartReportLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_base_chart_report_layout, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);
  }

  public void setMonthReportObject(ReportBaseModel reportBaseModel, double currentTotalAmount) {
    v_bar_chart.clearChart();

    double income = reportBaseModel.getIncome();
    double expense = reportBaseModel.getSpent();
    float incomeToTotal = 0.0f;
    if (currentTotalAmount != 0.0d) {
      incomeToTotal = Math.abs((float) ((income / currentTotalAmount) * TOTAL_PERCENTAGE_VALUE));
    }
    float expenseToTotal = 0.0f;
    if (currentTotalAmount != 0.0d) {
      expenseToTotal = Math.abs((float) ((expense / currentTotalAmount) * TOTAL_PERCENTAGE_VALUE));
    }

    BigDecimal roundIncomeToTotal =
        new BigDecimal(incomeToTotal).setScale(DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
    BigDecimal roundExpenseToTotal =
        new BigDecimal(expenseToTotal).setScale(DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);

    v_bar_chart.addBar(
        new BarModel(roundExpenseToTotal.floatValue(), getResources().getColor(R.color.red)));
    v_bar_chart.addBar(new BarModel(TOTAL_PERCENTAGE_VALUE, getResources().getColor(R.color.blue)));
    v_bar_chart.addBar(
        new BarModel(roundIncomeToTotal.floatValue(), getResources().getColor(R.color.green)));

    v_bar_chart.startAnimation();
  }
}
