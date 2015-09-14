package com.novachevskyi.expenseslite.presentation.presenter.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountsTotalAmountEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.accounts.AccountsGetTotalAmountImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.accounts.AccountModelDataMapper;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountsTotalAmountLoadView;

public class AccountsTotalAmountPresenter implements Presenter {

  private final AccountsTotalAmountLoadView accountsLoadView;

  private final AccountModelDataMapper accountModelDataMapper;

  private final AccountsGetTotalAmountImpl accountsGetTotalAmount;

  public AccountsTotalAmountPresenter(AccountsTotalAmountLoadView accountsLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.accountsGetTotalAmount = new AccountsGetTotalAmountImpl(postExecutionThread);

    this.accountModelDataMapper = new AccountModelDataMapper();

    this.accountsLoadView = accountsLoadView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize() {
    this.accountsGetTotalAmount.execute(accountsTotalAmountCallback);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.accountsLoadView.getContext(),
        errorBundle.getException());
    this.accountsLoadView.showError(errorMessage);
  }

  private final AccountsDataStore.AccountsTotalAmountCallback accountsTotalAmountCallback =
      new AccountsDataStore.AccountsTotalAmountCallback() {
        @Override
        public void onAccountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities) {
          AccountsTotalAmountPresenter.this.accountsTotalAmountLoaded(accountEntities);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AccountsTotalAmountPresenter.this.showErrorMessage(error);
        }
      };

  private void accountsTotalAmountLoaded(AccountsTotalAmountEntity accountEntities) {
    if (accountEntities != null && accountEntities.result != null) {
      this.accountsLoadView.totalAmountLoaded(accountEntities.result);
    }
  }
}
