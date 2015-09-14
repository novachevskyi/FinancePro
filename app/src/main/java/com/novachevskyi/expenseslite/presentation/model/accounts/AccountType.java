package com.novachevskyi.expenseslite.presentation.model.accounts;

public enum AccountType {
  CASH(0),
  CREDIT_CARD(1),
  DEBIT(2);

  AccountType(int i) {
    this.type = i;
  }

  private int type;

  public int getNumericType() {
    return type;
  }

  public static AccountType getAccountType(Integer paymentMethod) {
    if (paymentMethod != null) {
      switch (paymentMethod) {
        case 0:
          return AccountType.CASH;
        case 1:
          return AccountType.CREDIT_CARD;
        case 2:
          return AccountType.DEBIT;
      }
    }

    return AccountType.CASH;
  }
}