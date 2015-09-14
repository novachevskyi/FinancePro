package com.novachevskyi.expenseslite.presentation.presenter.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.accounts.AccountsCreateImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.accounts.AccountModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountType;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.utils.Validator;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountLoadView;

public class AddAccountPresenter implements Presenter {

  private final AccountLoadView accountLoadView;
  private final AccountsCreateImpl accountsCreate;
  private final AccountModelDataMapper accountModelDataMapper;

  private AccountEntity accountEntity;

  public AddAccountPresenter(AccountLoadView accountLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.accountsCreate = new AccountsCreateImpl(postExecutionThread);
    this.accountModelDataMapper = new AccountModelDataMapper();
    this.accountLoadView = accountLoadView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize(String name, double amount, AccountType accountType) {
    this.createNewAccount(name, amount, accountType);
  }

  private void createNewAccount(String name, double amount, AccountType accountType) {
    this.showViewLoading();

    accountEntity = new AccountEntity(name, amount, accountType.getNumericType());
    this.createAccount(accountEntity);
  }

  private void showViewLoading() {
    this.accountLoadView.showLoading();
  }

  private void hideViewLoading() {
    this.accountLoadView.hideLoading();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.accountLoadView.getContext(),
        errorBundle.getException());
    this.accountLoadView.showError(errorMessage);
  }

  private void accountLoaded(AccountEntity accountEntity) {
    if (accountEntity != null && this.accountEntity != null) {
      this.accountEntity.objectId = accountEntity.objectId;
    }
    final AccountModel accountModel = this.accountModelDataMapper.transform(this.accountEntity);
    this.accountLoadView.accountLoaded(accountModel);
  }

  private void createAccount(AccountEntity accountEntity) {
    this.accountsCreate.execute(this.accountDataCallback, accountEntity);
  }

  private final AccountsDataStore.AccountDataCallback accountDataCallback =
      new AccountsDataStore.AccountDataCallback() {
        @Override public void onAccountLoaded(AccountEntity accountEntity) {
          AddAccountPresenter.this.hideViewLoading();
          AddAccountPresenter.this.accountLoaded(accountEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AddAccountPresenter.this.hideViewLoading();
          AddAccountPresenter.this.showErrorMessage(error);
        }
      };

  public boolean checkName(String title) {
    String error = Validator.validateTitle(accountLoadView.getContext(), title);

    if (error == null) {
      accountLoadView.nameDataAccepted();
      return true;
    } else {
      accountLoadView.nameDataError(error);
      return false;
    }
  }

  public void validateData(String title) {
    if (checkName(title)) {
      accountLoadView.dataValidated();
    }
  }
}
