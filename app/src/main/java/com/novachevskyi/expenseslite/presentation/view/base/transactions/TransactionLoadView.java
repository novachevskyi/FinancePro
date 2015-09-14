package com.novachevskyi.expenseslite.presentation.view.base.transactions;

import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface TransactionLoadView extends LoadDataView {
  void transactionLoaded(TransactionModel transaction);

  void amountDataError(String message);

  void amountDataAccepted();

  void accountDataError(String message);

  void accountDataAccepted();
}
