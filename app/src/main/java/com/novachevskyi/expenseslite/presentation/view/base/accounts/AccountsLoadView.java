package com.novachevskyi.expenseslite.presentation.view.base.accounts;

import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadListDataView;
import java.util.List;

public interface AccountsLoadView extends LoadListDataView {
  void accountsLoaded(List<AccountModel> accounts, boolean isInitialLoad);
}
