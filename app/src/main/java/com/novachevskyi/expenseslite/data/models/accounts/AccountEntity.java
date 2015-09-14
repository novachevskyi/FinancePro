package com.novachevskyi.expenseslite.data.models.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountEntity {
  public String objectId;
  public String name;
  public Double amount;
  public Integer paymentMethod;
  public String userId;

  public AccountEntity() {

  }

  public AccountEntity(String name, double amount, int numericType) {
    this.name = name;
    this.amount = amount;
    this.paymentMethod = numericType;
  }
}
