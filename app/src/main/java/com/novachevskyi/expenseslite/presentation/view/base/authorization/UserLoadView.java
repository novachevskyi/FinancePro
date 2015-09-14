package com.novachevskyi.expenseslite.presentation.view.base.authorization;

import com.novachevskyi.expenseslite.presentation.model.users.UserModel;
import com.novachevskyi.expenseslite.presentation.view.base.LoadDataView;

public interface UserLoadView extends LoadDataView {
  void userLoaded(UserModel user);
}
