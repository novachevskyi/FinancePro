package com.novachevskyi.expenseslite.presentation.view.base.transactions;

import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadListDataView;
import java.util.List;

public interface TransactionsLoadView extends LoadListDataView {
  void transactionsLoaded(List<TransactionModel> transactionModels, boolean isInitialLoad);
}
