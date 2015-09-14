package com.novachevskyi.expenseslite.presentation.mapper.reports;

import com.novachevskyi.expenseslite.data.models.reports.CategoryReportEntity;
import com.novachevskyi.expenseslite.presentation.model.reports.CategoryReportModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CategoryReportModelDataMapper {

  public CategoryReportModel transform(CategoryReportEntity categoryReportEntity) {
    CategoryReportModel categoryReportModel = new CategoryReportModel();

    categoryReportModel.setCategory(Category.getCategory(categoryReportEntity.category));
    if (categoryReportEntity.amount != null) {
      categoryReportModel.setAmount(categoryReportEntity.amount);
    }

    return categoryReportModel;
  }

  public Collection<CategoryReportModel> transform(
      CategoryReportEntity[] categoryReportEntities) {
    Collection<CategoryReportModel> categoryReportModelsCollection;

    if (categoryReportEntities != null && categoryReportEntities.length > 0) {
      categoryReportModelsCollection = new ArrayList<>();
      for (CategoryReportEntity categoryReportEntity : categoryReportEntities) {
        categoryReportModelsCollection.add(transform(categoryReportEntity));
      }
    } else {
      categoryReportModelsCollection = Collections.emptyList();
    }

    return categoryReportModelsCollection;
  }
}
