package com.novachevskyi.expenseslite.presentation.mapper.reports;

import com.novachevskyi.expenseslite.data.models.reports.ReportBaseEntity;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportBaseModel;

public class ReportBaseModelDataMapper {

  public ReportBaseModel transform(ReportBaseEntity reportBaseEntity) {
    ReportBaseModel reportBaseModel = new ReportBaseModel();

    if (reportBaseEntity.income != null) {
      reportBaseModel.setIncome(reportBaseEntity.income);
    }
    if (reportBaseEntity.spent != null) {
      reportBaseModel.setSpent(reportBaseEntity.spent);
    }

    return reportBaseModel;
  }
}
