package com.novachevskyi.expenseslite.presentation.mapper.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.presentation.model.users.UserModel;

public class UserModelDataMapper {
  public UserModel transform(UserEntity user) {
    UserModel userModel = new UserModel();
    userModel.setUserId(user.objectId);
    userModel.setUserName(user.username);
    userModel.setEmail(user.email);

    return userModel;
  }
}
