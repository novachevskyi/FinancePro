package com.novachevskyi.expenseslite.presentation.mapper.reports;

import com.novachevskyi.expenseslite.data.models.reports.ReportWithCategoriesEntity;
import com.novachevskyi.expenseslite.presentation.model.reports.ReportWithCategoriesModel;
import java.util.ArrayList;

public class ReportWithCategoriesModelDataMapper extends ReportBaseModelDataMapper {

  public ReportWithCategoriesModel transform(ReportWithCategoriesEntity reportBaseEntity) {
    ReportWithCategoriesModel reportWithCategoriesModel =
        new ReportWithCategoriesModel(super.transform(reportBaseEntity));

    CategoryReportModelDataMapper mapper = new CategoryReportModelDataMapper();
    reportWithCategoriesModel.setIncomeByCategories(
        new ArrayList(mapper.transform(reportBaseEntity.incomeByCategories)));
    reportWithCategoriesModel.setSpentByCategories(
        new ArrayList(mapper.transform(reportBaseEntity.spentByCategories)));

    return reportWithCategoriesModel;
  }
}
