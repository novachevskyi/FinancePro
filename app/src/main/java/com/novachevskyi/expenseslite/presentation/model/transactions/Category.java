package com.novachevskyi.expenseslite.presentation.model.transactions;

import android.content.Context;
import com.novachevskyi.expenseslite.R;

public enum Category {
  TRANSFER(-1),

  ACCESSORIES(0),
  ENTERTAINMENT(1),
  EVENTS(2),
  FOOD(3),
  FUEL(4),
  GIFTS(5),
  LEISURE(6),
  MANAGEMENT_FEES(7),
  OTHER(8),
  RESTAURANTS(9),
  SALARIES(10),
  SPORTS(11),
  STUDIES(12),
  TRANSPORTATION(13),
  VACATIONS(14);

  Category(int i) {
    this.type = i;
  }

  private int type;

  public int getNumericType() {
    return type;
  }

  public static Category getCategory(Integer paymentMethod) {
    if (paymentMethod != null) {
      switch (paymentMethod) {
        case -1:
          return Category.TRANSFER;
        case 0:
          return Category.ACCESSORIES;
        case 1:
          return Category.ENTERTAINMENT;
        case 2:
          return Category.EVENTS;
        case 3:
          return Category.FOOD;
        case 4:
          return Category.FUEL;
        case 5:
          return Category.GIFTS;
        case 6:
          return Category.LEISURE;
        case 7:
          return Category.MANAGEMENT_FEES;
        case 8:
          return Category.OTHER;
        case 9:
          return Category.RESTAURANTS;
        case 10:
          return Category.SALARIES;
        case 11:
          return Category.SPORTS;
        case 12:
          return Category.STUDIES;
        case 13:
          return Category.TRANSPORTATION;
        case 14:
          return Category.VACATIONS;
      }
    }

    return Category.OTHER;
  }

  public static String getCategoryTitleExpanded(Category category, Context context) {
    if (category != null) {
      if(category == TRANSFER) {
        return context.getString(
            R.string.enum_category_title_transfer);
      } else {
        return getCategoryTitle(category, context);
      }
    }

    return null;
  }

  public static String getCategoryTitle(Category category, Context context) {
    if (category != null) {
      switch (category) {
        case ACCESSORIES:
          return context.getString(
              R.string.enum_category_title_accessories);
        case ENTERTAINMENT:
          return context.getString(
              R.string.enum_category_title_entertainment);
        case EVENTS:
          return context.getString(R.string.enum_category_title_events);
        case FOOD:
          return context.getString(R.string.enum_category_title_food);
        case FUEL:
          return context.getString(R.string.enum_category_title_fuel);
        case GIFTS:
          return context.getString(R.string.enum_category_title_gifts);
        case LEISURE:
          return context.getString(R.string.enum_category_title_leisure);
        case MANAGEMENT_FEES:
          return context.getString(
              R.string.enum_category_title_management_fees);
        case OTHER:
          return context.getString(R.string.enum_category_title_other);
        case RESTAURANTS:
          return context.getString(
              R.string.enum_category_title_restaurants);
        case SALARIES:
          return context.getString(
              R.string.enum_category_title_salaries);
        case SPORTS:
          return context.getString(R.string.enum_category_title_sports);
        case STUDIES:
          return context.getString(R.string.enum_category_title_studies);
        case TRANSPORTATION:
          return context.getString(
              R.string.enum_category_title_transportation);
        case VACATIONS:
          return context.getString(
              R.string.enum_category_title_vacations);
      }
    }

    return null;
  }
}