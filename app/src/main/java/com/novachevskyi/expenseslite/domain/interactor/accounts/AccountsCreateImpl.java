package com.novachevskyi.expenseslite.domain.interactor.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.AccountsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;

public class AccountsCreateImpl extends InteractorBase {

  private final AccountsDataRepository accountRepository;

  AccountEntity accountEntity;

  public AccountsCreateImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    accountRepository = AccountsDataRepository.getInstance();
  }

  public void execute(AccountsDataStore.AccountDataCallback callback,
      AccountEntity accountEntity) {
    this.accountEntity = accountEntity;

    executeBase(callback);
  }

  @Override public void run() {
    accountRepository.create(accountEntity, repositoryCallback);
  }

  protected final AccountsDataStore.AccountDataCallback repositoryCallback =
      new AccountsDataStore.AccountDataCallback() {
        @Override public void onAccountLoaded(AccountEntity accountEntity) {
          notifyGetListSuccess(accountEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final AccountEntity accountEntity) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((AccountsDataStore.AccountDataCallback) callback).onAccountLoaded(
            accountEntity);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((AccountsDataStore.AccountDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
