package com.novachevskyi.expenseslite.presentation.model.main;

public enum DrawerMenuType {
  ACCOUNTS(0),
  TRANSACTIONS(1),
  BUDGETS(2),
  GENERAL_REPORT(3),
  YEAR_REPORT(4),
  CATEGORIES_REPORT(5);

  DrawerMenuType(int i) {
    this.type = i;
  }

  private int type;

  public int getNumericType() {
    return type;
  }

  public static DrawerMenuType getTransactionType(Integer paymentMethod) {
    if (paymentMethod != null) {
      switch (paymentMethod) {
        case 0:
          return DrawerMenuType.ACCOUNTS;
        case 1:
          return DrawerMenuType.TRANSACTIONS;
        case 2:
          return DrawerMenuType.BUDGETS;
        case 3:
          return DrawerMenuType.GENERAL_REPORT;
        case 4:
          return DrawerMenuType.YEAR_REPORT;
        case 5:
          return DrawerMenuType.CATEGORIES_REPORT;
      }
    }

    return DrawerMenuType.BUDGETS;
  }
}