package com.novachevskyi.expenseslite.presentation.presenter.authorization;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.users.UsersSignUpImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.users.UserModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.users.UserModel;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoadView;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserRegistrationEditView;

public class RegistrationPresenter extends RegistrationEditPresenter {

  private final UserLoadView viewDetailsView;
  private final UsersSignUpImpl signUp;
  private final UserModelDataMapper userModelDataMapper;

  public RegistrationPresenter(UserLoadView userDetailsView,
      UserRegistrationEditView userRegistrationEditView) {
    super(userRegistrationEditView);

    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.signUp = new UsersSignUpImpl(postExecutionThread);
    this.userModelDataMapper = new UserModelDataMapper();
    this.viewDetailsView = userDetailsView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize(String username, String password, String email) {
    this.loadCurrentUser(username, password, email);
  }

  private void loadCurrentUser(String username, String password, String email) {
    this.showViewLoading();

    this.getUserDetails(new UserEntity(username, password, email));
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

  private void getUserDetails(UserEntity userEntity) {
    this.signUp.execute(this.userDetailsCallback, userEntity);
  }

  private final UsersDataStore.UserDataCallback userDetailsCallback =
      new UsersDataStore.UserDataCallback() {
        @Override public void onUserLoaded(UserEntity user) {
          RegistrationPresenter.this.hideViewLoading();
          RegistrationPresenter.this.userLoaded(user);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          RegistrationPresenter.this.hideViewLoading();
          RegistrationPresenter.this.showErrorMessage(error);
        }
      };

  public void validateData(String login, String password, String confirm, String email) {
    if (checkLogin(login)
        && checkPassword(password)
        && checkPasswordConfirm(password, confirm)
        && checkEmail(email)) {
      this.viewDetailsView.dataValidated();
    }
  }
}
