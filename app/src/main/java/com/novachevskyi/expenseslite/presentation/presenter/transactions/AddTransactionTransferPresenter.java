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
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.utils.Validator;
import com.novachevskyi.expenseslite.presentation.view.base.transactions.TransactionTransferLoadView;

public class AddTransactionTransferPresenter implements Presenter {

  private final TransactionTransferLoadView transactionTransferLoadView;
  private final TransactionsCreateImpl transactionsCreate;
  private final TransactionModelDataMapper transactionModelDataMapper;

  private TransactionEntity sourceTransactionEntity;
  private TransactionEntity destinationTransactionEntity;

  private TransactionModel sourceTransactionModel;

  public AddTransactionTransferPresenter(TransactionTransferLoadView transactionTransferLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.transactionsCreate = new TransactionsCreateImpl(postExecutionThread);
    this.transactionModelDataMapper = new TransactionModelDataMapper();
    this.transactionTransferLoadView = transactionTransferLoadView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize(String paymentDate, String sourceAccountId,
      String destinationAccountId, double amount, String notes, String payee,
      String destAccountTitle) {
    this.createNewTransaction(paymentDate, sourceAccountId, destinationAccountId, amount, notes,
        payee, destAccountTitle);
  }

  private void createNewTransaction(String paymentDate, String sourceAccountId,
      String destinationAccountId, double amount, String notes, String payee,
      String destAccountTitle) {
    this.showViewLoading();

    sourceTransactionEntity = new TransactionEntity(paymentDate, sourceAccountId, amount, notes,
        TransactionType.OUTCOME.getNumericType(), destAccountTitle,
        Category.TRANSFER.getNumericType());
    destinationTransactionEntity =
        new TransactionEntity(paymentDate, destinationAccountId, amount, notes,
            TransactionType.INCOME.getNumericType(), payee, Category.TRANSFER.getNumericType());

    this.createSourceTransaction(sourceTransactionEntity);
  }

  private void showViewLoading() {
    this.transactionTransferLoadView.showLoading();
  }

  private void hideViewLoading() {
    this.transactionTransferLoadView.hideLoading();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.transactionTransferLoadView.getContext(),
        errorBundle.getException());
    this.transactionTransferLoadView.showError(errorMessage);
  }

  private void sourceTransactionLoaded(TransactionEntity transactionEntity) {
    if (transactionEntity != null && this.sourceTransactionEntity != null) {
      this.sourceTransactionEntity.objectId = transactionEntity.objectId;
    }
    sourceTransactionModel =
        this.transactionModelDataMapper.transform(this.sourceTransactionEntity);

    createDestinationTransaction(destinationTransactionEntity);
  }

  private void destinationTransactionLoaded(TransactionEntity transactionEntity) {
    if (transactionEntity != null && this.destinationTransactionEntity != null) {
      this.destinationTransactionEntity.objectId = transactionEntity.objectId;
    }
    TransactionModel destinationTransactionModel =
        this.transactionModelDataMapper.transform(this.destinationTransactionEntity);

    this.transactionTransferLoadView.transactionsLoaded(sourceTransactionModel,
        destinationTransactionModel);
  }

  private void createDestinationTransaction(TransactionEntity transactionEntity) {
    this.transactionsCreate.execute(this.destinationTransactionDataCallback, transactionEntity);
  }

  private void createSourceTransaction(TransactionEntity transactionEntity) {
    this.transactionsCreate.execute(this.sourceTransactionDataCallback, transactionEntity);
  }

  private final TransactionsDataStore.TransactionDataCallback destinationTransactionDataCallback =
      new TransactionsDataStore.TransactionDataCallback() {
        @Override public void onTransactionLoaded(TransactionEntity transactionEntity) {
          AddTransactionTransferPresenter.this.hideViewLoading();
          AddTransactionTransferPresenter.this.destinationTransactionLoaded(transactionEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AddTransactionTransferPresenter.this.hideViewLoading();
          AddTransactionTransferPresenter.this.showErrorMessage(error);
        }
      };

  private final TransactionsDataStore.TransactionDataCallback sourceTransactionDataCallback =
      new TransactionsDataStore.TransactionDataCallback() {
        @Override public void onTransactionLoaded(TransactionEntity transactionEntity) {
          AddTransactionTransferPresenter.this.sourceTransactionLoaded(transactionEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AddTransactionTransferPresenter.this.hideViewLoading();
          AddTransactionTransferPresenter.this.showErrorMessage(error);
        }
      };

  public boolean checkAmount(String title) {
    String error = Validator.validateAmount(transactionTransferLoadView.getContext(), title);

    if (error == null) {
      transactionTransferLoadView.amountDataAccepted();
      return true;
    } else {
      transactionTransferLoadView.amountDataError(error);
      return false;
    }
  }

  public void validateData(String amount, AccountModel sourceAccountModel,
      AccountModel destinationAccountModel) {
    if (checkAmount(amount) && checkSourceAccountModel(sourceAccountModel)
        && checkDestinationAccountModel(destinationAccountModel)) {
      transactionTransferLoadView.dataValidated();
    }
  }

  private boolean checkSourceAccountModel(AccountModel selectedAccountModel) {
    String error = Validator.validateAccountModel(transactionTransferLoadView.getContext(),
        selectedAccountModel);

    if (error == null) {
      transactionTransferLoadView.sourceAccountDataAccepted();
      return true;
    } else {
      transactionTransferLoadView.sourceAccountDataError(error);
      return false;
    }
  }

  private boolean checkDestinationAccountModel(AccountModel selectedAccountModel) {
    String error = Validator.validateAccountModel(transactionTransferLoadView.getContext(),
        selectedAccountModel);

    if (error == null) {
      transactionTransferLoadView.destinationAccountDataAccepted();
      return true;
    } else {
      transactionTransferLoadView.destinationAccountDataError(error);
      return false;
    }
  }
}
