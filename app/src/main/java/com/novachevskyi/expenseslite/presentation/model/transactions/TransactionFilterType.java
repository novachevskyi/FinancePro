package com.novachevskyi.expenseslite.presentation.model.transactions;

public enum TransactionFilterType {
  TODAY(0),
  THIS_MONTH(1),
  MONTH(2),
  ACCOUNT(3),
  BUDGET(4);

  TransactionFilterType(int i) {
    this.type = i;
  }

  private int type;

  public int getNumericType() {
    return type;
  }

  public static TransactionFilterType getTransactionFilterType(Integer paymentMethod) {
    if (paymentMethod != null) {
      switch (paymentMethod) {
        case 0:
          return TransactionFilterType.TODAY;
        case 1:
          return TransactionFilterType.THIS_MONTH;
        case 2:
          return TransactionFilterType.MONTH;
        case 3:
          return TransactionFilterType.ACCOUNT;
        case 4:
          return TransactionFilterType.BUDGET;
      }
    }

    return TransactionFilterType.TODAY;
  }
}