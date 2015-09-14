package com.novachevskyi.expenseslite.presentation.view.base.accounts;

import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface AccountsTotalAmountLoadView extends LoadDataView {
  void totalAmountLoaded(double totalAmount);
}
