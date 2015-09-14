package com.novachevskyi.expenseslite.presentation.view.base.accounts;

import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface AccountLoadView extends LoadDataView {
  void accountLoaded(AccountModel account);

  void nameDataError(String message);

  void nameDataAccepted();
}
