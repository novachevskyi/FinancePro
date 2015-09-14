package com.novachevskyi.expenseslite.presentation.view.base.reports;

import com.novachevskyi.expenseslite.presentation.model.reports.MonthReportModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadListDataView;
import java.util.List;

public interface ReportsMonthLoadView extends LoadListDataView {
  void reportsLoaded(List<MonthReportModel> reports, boolean isInitialLoad);
}
