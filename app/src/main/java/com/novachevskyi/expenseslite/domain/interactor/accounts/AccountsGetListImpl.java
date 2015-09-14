package com.novachevskyi.expenseslite.domain.interactor.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.AccountsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;

public class AccountsGetListImpl extends InteractorBase {

  private int limit;
  private int skip;

  private final AccountsDataRepository accountRepository;

  public AccountsGetListImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    accountRepository = AccountsDataRepository.getInstance();
  }

  public void execute(AccountsDataStore.AccountsDataCallback callback, int limit, int skip) {
    this.limit = limit;
    this.skip = skip;

    executeBase(callback);
  }

  @Override public void run() {
    accountRepository.getList(limit, skip, repositoryCallback);
  }

  protected final AccountsDataStore.AccountsDataCallback repositoryCallback =
      new AccountsDataStore.AccountsDataCallback() {
        @Override public void onAccountsLoaded(AccountsEntity accountEntities) {
          notifyGetListSuccess(accountEntities);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final AccountsEntity accountEntities) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((AccountsDataStore.AccountsDataCallback) callback).onAccountsLoaded(accountEntities);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((AccountsDataStore.AccountsDataCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
