package com.novachevskyi.expenseslite.presentation.presenter.authorization;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.users.UsersLoginImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.users.UserModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.users.UserModel;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoadView;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoginEditView;

public class LoginPresenter extends LoginEditPresenter {

  private final UserLoadView viewDetailsView;
  private final UsersLoginImpl login;
  private final UserModelDataMapper userModelDataMapper;

  public LoginPresenter(UserLoadView userDetailsView, UserLoginEditView userLoginEditView) {
    super(userLoginEditView);

    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.login = new UsersLoginImpl(postExecutionThread);
    this.userModelDataMapper = new UserModelDataMapper();
    this.viewDetailsView = userDetailsView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize(String username, String password) {
    this.loadCurrentUser(username, password);
  }

  private void loadCurrentUser(String username, String password) {
    this.showViewLoading();
    this.getUserDetails(username, password);
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void userLoaded(UserEntity user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.viewDetailsView.userLoaded(userModel);
  }

  private void getUserDetails(String username, String password) {
    this.login.execute(this.userDetailsCallback, username, password);
  }

  private final UsersDataStore.UserDataCallback userDetailsCallback =
      new UsersDataStore.UserDataCallback() {
        @Override public void onUserLoaded(UserEntity user) {
          LoginPresenter.this.hideViewLoading();
          LoginPresenter.this.userLoaded(user);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          LoginPresenter.this.hideViewLoading();
          LoginPresenter.this.showErrorMessage(error);
        }
      };

  public void validateData(String userName, String password) {
    if(checkLogin(userName) && checkPassword(password))
    {
      this.viewDetailsView.dataValidated();
    }
  }
}
