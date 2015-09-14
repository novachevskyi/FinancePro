package com.novachevskyi.expenseslite.presentation.view.base.reports;

import com.novachevskyi.expenseslite.presentation.model.reports.GeneralReportModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface ReportsGeneralLoadView extends LoadDataView {
  void generalReportLoaded(GeneralReportModel generalReportModel);

  void showMainView();

  void hideMainView();
}
