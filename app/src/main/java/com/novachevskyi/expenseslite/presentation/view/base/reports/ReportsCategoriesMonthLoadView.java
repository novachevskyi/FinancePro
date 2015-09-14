package com.novachevskyi.expenseslite.presentation.view.base.reports;

import com.novachevskyi.expenseslite.presentation.model.reports.ReportWithCategoriesModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface ReportsCategoriesMonthLoadView extends LoadDataView {
  void categoriesMonthReportLoaded(ReportWithCategoriesModel reportWithCategoriesModel);

  void showMainView();

  void hideMainView();
}
