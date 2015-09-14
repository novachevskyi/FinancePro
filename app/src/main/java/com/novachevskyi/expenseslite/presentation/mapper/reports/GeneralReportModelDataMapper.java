package com.novachevskyi.expenseslite.presentation.mapper.reports;

import com.novachevskyi.expenseslite.data.models.reports.GeneralReportEntity;
import com.novachevskyi.expenseslite.presentation.model.reports.GeneralReportModel;

public class GeneralReportModelDataMapper {

  public GeneralReportModel transform(GeneralReportEntity generalReportEntity) {
    GeneralReportModel generalReportModel = new GeneralReportModel();

    if (generalReportEntity.todaySpentAmount != null) {
      generalReportModel.setTodaySpentAmount(generalReportEntity.todaySpentAmount);
    }

    ReportBaseModelDataMapper reportBaseModelDataMapper = new ReportBaseModelDataMapper();
    generalReportModel.setThisMonthReport(
        reportBaseModelDataMapper.transform(generalReportEntity.thisMonthReport));

    return generalReportModel;
  }
}
