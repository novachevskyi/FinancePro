package com.novachevskyi.expenseslite.presentation.mapper.reports;

import com.novachevskyi.expenseslite.data.models.reports.MonthReportEntity;
import com.novachevskyi.expenseslite.presentation.model.reports.MonthReportModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MonthReportModelDataMapper {

  public MonthReportModel transform(MonthReportEntity monthReportEntity) {
    MonthReportModel monthReportModel = new MonthReportModel();

    if (monthReportEntity.month != null) {
      monthReportModel.setMonth(monthReportEntity.month);
    }

    ReportBaseModelDataMapper reportBaseModelDataMapper = new ReportBaseModelDataMapper();
    monthReportModel.setMonthReport(
        reportBaseModelDataMapper.transform(monthReportEntity.monthReport));

    return monthReportModel;
  }

  public Collection<MonthReportModel> transform(
      MonthReportEntity[] monthReportEntities) {
    Collection<MonthReportModel> monthReportModelCollection;

    if (monthReportEntities != null && monthReportEntities.length > 0) {
      monthReportModelCollection = new ArrayList<>();
      for (MonthReportEntity monthReportEntity : monthReportEntities) {
        monthReportModelCollection.add(transform(monthReportEntity));
      }
    } else {
      monthReportModelCollection = Collections.emptyList();
    }

    return monthReportModelCollection;
  }
}
