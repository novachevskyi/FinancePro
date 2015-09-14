package com.novachevskyi.expenseslite.presentation.presenter.transactions;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.transactions.TransactionsCreateImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.transactions.TransactionsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.transactions.TransactionModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.utils.Validator;
import com.novachevskyi.expenseslite.presentation.view.base.transactions.TransactionLoadView;

public class AddTransactionPresenter implements Presenter {

  private final TransactionLoadView transactionLoadView;
  private final TransactionsCreateImpl transactionsCreate;
  private final TransactionModelDataMapper transactionModelDataMapper;

  private TransactionEntity transactionEntity;

  public AddTransactionPresenter(TransactionLoadView transactionLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.transactionsCreate = new TransactionsCreateImpl(postExecutionThread);
    this.transactionModelDataMapper = new TransactionModelDataMapper();
    this.transactionLoadView = transactionLoadView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize(String paymentDate, String accountId, double amount, String notes,
      int type, String payee, int category) {
    this.createNewTransaction(paymentDate, accountId, amount, notes, type, payee, category);
  }

  private void createNewTransaction(String paymentDate, String accountId, double amount, String notes,
      int type, String payee, int category) {
    this.showViewLoading();

    transactionEntity = new TransactionEntity(paymentDate, accountId, amount, notes, type, payee, category);
    this.createTransaction(transactionEntity);
  }

  private void showViewLoading() {
    this.transactionLoadView.showLoading();
  }

  private void hideViewLoading() {
    this.transactionLoadView.hideLoading();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.transactionLoadView.getContext(),
        errorBundle.getException());
    this.transactionLoadView.showError(errorMessage);
  }

  private void transactionLoaded(TransactionEntity transactionEntity) {
    if (transactionEntity != null && this.transactionEntity != null) {
      this.transactionEntity.objectId = transactionEntity.objectId;
    }
    final TransactionModel transactionModel =
        this.transactionModelDataMapper.transform(this.transactionEntity);
    this.transactionLoadView.transactionLoaded(transactionModel);
  }

  private void createTransaction(TransactionEntity transactionEntity) {
    this.transactionsCreate.execute(this.transactionDataCallback, transactionEntity);
  }

  private final TransactionsDataStore.TransactionDataCallback transactionDataCallback =
      new TransactionsDataStore.TransactionDataCallback() {
        @Override public void onTransactionLoaded(TransactionEntity transactionEntity) {
          AddTransactionPresenter.this.hideViewLoading();
          AddTransactionPresenter.this.transactionLoaded(transactionEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AddTransactionPresenter.this.hideViewLoading();
          AddTransactionPresenter.this.showErrorMessage(error);
        }
      };

  public boolean checkAmount(String title) {
    String error = Validator.validateAmount(transactionLoadView.getContext(), title);

    if (error == null) {
      transactionLoadView.amountDataAccepted();
      return true;
    } else {
      transactionLoadView.amountDataError(error);
      return false;
    }
  }

  public void validateData(String amount, AccountModel selectedAccountModel) {
    if (checkAmount(amount) && checkAccountModel(selectedAccountModel)) {
      transactionLoadView.dataValidated();
    }
  }

  private boolean checkAccountModel(AccountModel selectedAccountModel) {
    String error = Validator.validateAccountModel(transactionLoadView.getContext(), selectedAccountModel);

    if (error == null) {
      transactionLoadView.accountDataAccepted();
      return true;
    } else {
      transactionLoadView.accountDataError(error);
      return false;
    }
  }
}
