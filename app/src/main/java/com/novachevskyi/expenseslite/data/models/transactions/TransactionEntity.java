package com.novachevskyi.expenseslite.data.models.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionEntity {
  public String objectId;
  public String paymentDate;
  public String accountId;
  public String userId;
  public Double amount;
  public String notes;
  public Integer type;
  public Integer category;
  public String payee;

  public TransactionEntity() {

  }

  public TransactionEntity(String paymentDate, String accountId, double amount, String notes,
      int type, String payee, int category) {
    this.paymentDate = paymentDate;
    this.accountId = accountId;
    this.amount = amount;
    this.notes = notes;
    this.type = type;
    this.payee = payee;
    this.category = category;
  }
}
