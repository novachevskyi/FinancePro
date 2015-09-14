package com.novachevskyi.expenseslite.presentation.view.base.transactions;

import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface TransactionTransferLoadView extends LoadDataView {
  void transactionsLoaded(TransactionModel source, TransactionModel destination);

  void amountDataError(String message);

  void amountDataAccepted();

  void sourceAccountDataError(String message);

  void sourceAccountDataAccepted();

  void destinationAccountDataError(String message);

  void destinationAccountDataAccepted();
}
