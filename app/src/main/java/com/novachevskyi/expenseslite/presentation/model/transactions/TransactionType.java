package com.novachevskyi.expenseslite.presentation.model.transactions;

public enum TransactionType {
  INCOME(0),
  OUTCOME(1);

  TransactionType(int i) {
    this.type = i;
  }

  private int type;

  public int getNumericType() {
    return type;
  }

  public static TransactionType getTransactionType(Integer paymentMethod) {
    if (paymentMethod != null) {
      switch (paymentMethod) {
        case 0:
          return TransactionType.INCOME;
        case 1:
          return TransactionType.OUTCOME;
      }
    }

    return TransactionType.INCOME;
  }
}