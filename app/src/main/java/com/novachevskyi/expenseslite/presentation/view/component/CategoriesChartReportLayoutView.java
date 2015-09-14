package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.reports.CategoryReportModel;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportWithCategoriesModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import java.math.BigDecimal;
import java.util.List;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class CategoriesChartReportLayoutView extends LinearLayout {

  private final static float TOTAL_PERCENTAGE_VALUE = 100.0f;
  private final static int DECIMAL_NUMBER = 2;

  private final static float HEIGHT_PERCENTAGE = 0.375f;

  @InjectView(R.id.v_pie_chart) PieChart v_pie_chart;

  private ReportWithCategoriesModel categoriesModel;

  public CategoriesChartReportLayoutView(Context context) {
    super(context);
    init();
  }

  public CategoriesChartReportLayoutView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CategoriesChartReportLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_categories_chart_report_layout, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

    float height = displayMetrics.heightPixels;
    float layoutHeight = height * HEIGHT_PERCENTAGE;

    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, (int) layoutHeight);
    v_pie_chart.setLayoutParams(layoutParams);

    setDefault();
  }

  private void setDefault() {
    v_pie_chart.clearChart();
    v_pie_chart.addPieSlice(new PieModel(
        getContext().getString(R.string.view_categories_chart_report_layout_no_categories_expenses),
        0, getResources().getColor(R.color.half_black)));
  }

  public void setCategoriesReport(ReportWithCategoriesModel reportWithCategoriesModel) {
    this.categoriesModel = reportWithCategoriesModel;
  }

  public void displayExpenses() {
    if (this.categoriesModel != null) {
      List<CategoryReportModel> categoriesModel = this.categoriesModel.getSpentByCategories();
      double totalSpent = this.categoriesModel.getSpent();

      displayChart(categoriesModel, totalSpent);
    } else {
      setDefault();
    }
  }

  public void displayIncomes() {
    if (categoriesModel != null) {
      List<CategoryReportModel> categoriesModel = this.categoriesModel.getIncomeByCategories();
      double totalSpent = this.categoriesModel.getIncome();

      displayChart(categoriesModel, totalSpent);
    } else {
      setDefault();
    }
  }

  private void displayChart(List<CategoryReportModel> categoriesModel, double totalSpent) {
    v_pie_chart.clearChart();
    for (CategoryReportModel categoryReportModel : categoriesModel) {
      String categoryTitle = Category.getCategoryTitle(categoryReportModel.getCategory(), getContext());
      if (categoryTitle != null) {
        int categoryColor = getCategoryColor(categoryReportModel.getCategory());

        double amount = categoryReportModel.getAmount();
        float expenseToTotal =
            Math.abs((float) ((amount / totalSpent) * TOTAL_PERCENTAGE_VALUE));
        BigDecimal roundExpenseToTotal =
            new BigDecimal(expenseToTotal).setScale(DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);

        v_pie_chart.addPieSlice(new PieModel(categoryTitle, roundExpenseToTotal.floatValue(),
            categoryColor));
      }
    }
    v_pie_chart.animate();
  }

  private int getCategoryColor(Category category) {
    if (category != null) {
      switch (category) {
        case ACCESSORIES:
          return getResources().getColor(R.color.blue);
        case ENTERTAINMENT:
          return getResources().getColor(R.color.purple);
        case EVENTS:
          return getResources().getColor(R.color.amber);
        case FOOD:
          return getResources().getColor(R.color.pink);
        case FUEL:
          return getResources().getColor(R.color.deep_purple);
        case GIFTS:
          return getResources().getColor(R.color.deep_orange);
        case LEISURE:
          return getResources().getColor(R.color.green);
        case MANAGEMENT_FEES:
          return getResources().getColor(R.color.red);
        case OTHER:
          return getResources().getColor(R.color.teal);
        case RESTAURANTS:
          return getResources().getColor(R.color.light_blue);
        case SALARIES:
          return getResources().getColor(R.color.indigo_pressed);
        case SPORTS:
          return getResources().getColor(R.color.cyan);
        case STUDIES:
          return getResources().getColor(R.color.red_pressed);
        case TRANSPORTATION:
          return getResources().getColor(R.color.green_pressed);
        case VACATIONS:
          return getResources().getColor(R.color.deep_orange_pressed);
      }
    }

    return 0;
  }
}
