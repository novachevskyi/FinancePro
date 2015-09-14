package com.novachevskyi.expenseslite.domain.interactor.users;

import com.novachevskyi.expenseslite.data.models.users.UserEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.UsersDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.users.UsersDataStore;

public abstract class UsersBaseImpl extends InteractorBase {
  protected final UsersDataRepository userRepository;

  public UsersBaseImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    userRepository = UsersDataRepository.getInstance();
  }

  protected final UsersDataStore.UserDataCallback repositoryCallback =
      new UsersDataStore.UserDataCallback() {
        @Override public void onUserLoaded(UserEntity user) {
          notifyGetCurrentUserSuccess(user);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetCurrentUserSuccess(final UserEntity user) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((UsersDataStore.UserDataCallback) callback).onUserLoaded(user);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((UsersDataStore.UserDataCallback) callback).onError((RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
