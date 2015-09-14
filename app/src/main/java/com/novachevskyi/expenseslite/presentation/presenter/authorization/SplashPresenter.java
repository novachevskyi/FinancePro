package com.novachevskyi.expenseslite.presentation.presenter.authorization;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.users.UsersGetCurrentUserImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.users.UserModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.users.UserModel;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoadView;

public class SplashPresenter implements Presenter {

  private final UserLoadView viewDetailsView;
  private final UsersGetCurrentUserImpl getCurrentUser;
  private final UserModelDataMapper userModelDataMapper;

  public SplashPresenter(UserLoadView userDetailsView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.getCurrentUser = new UsersGetCurrentUserImpl(postExecutionThread);

    this.userModelDataMapper = new UserModelDataMapper();

    this.viewDetailsView = userDetailsView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize() {
    this.loadCurrentUser();
  }

  private void loadCurrentUser() {
    this.showViewLoading();
    this.getUserDetails();
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

  private void getUserDetails() {
    this.getCurrentUser.execute(this.userDetailsCallback);
  }

  private final UsersDataStore.UserDataCallback userDetailsCallback =
      new UsersDataStore.UserDataCallback() {
        @Override public void onUserLoaded(UserEntity user) {
          SplashPresenter.this.hideViewLoading();
          SplashPresenter.this.userLoaded(user);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          SplashPresenter.this.hideViewLoading();
          SplashPresenter.this.showErrorMessage(error);
        }
      };
}
