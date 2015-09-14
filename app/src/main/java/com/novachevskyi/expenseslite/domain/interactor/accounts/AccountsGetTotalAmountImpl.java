package com.novachevskyi.expenseslite.domain.interactor.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.InteractorBase;
import com.novachevskyi.expenseslite.domain.repository.AccountsDataRepository;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;

public class AccountsGetTotalAmountImpl extends InteractorBase {

  private final AccountsDataRepository accountRepository;

  public AccountsGetTotalAmountImpl(PostExecutionThread postExecutionThread) {
    super(postExecutionThread);

    accountRepository = AccountsDataRepository.getInstance();
  }

  public void execute(AccountsDataStore.AccountsTotalAmountCallback callback) {
    executeBase(callback);
  }

  @Override public void run() {
    accountRepository.getTotalAmount(repositoryCallback);
  }

  protected final AccountsDataStore.AccountsTotalAmountCallback repositoryCallback =
      new AccountsDataStore.AccountsTotalAmountCallback() {
        @Override
        public void onAccountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities) {
          notifyGetListSuccess(accountEntities);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          notifyError(error);
        }
      };

  private void notifyGetListSuccess(final AccountsTotalAmountEntity accountEntities) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((AccountsDataStore.AccountsTotalAmountCallback) callback).onAccountsTotalAmountLoaded(
            accountEntities);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    postExecutionThread.post(new Runnable() {
      @Override public void run() {
        ((AccountsDataStore.AccountsTotalAmountCallback) callback).onError(
            (RepositoryErrorBundle) errorBundle);
      }
    });
  }
}
