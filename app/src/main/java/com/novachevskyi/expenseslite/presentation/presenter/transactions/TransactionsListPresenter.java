package com.novachevskyi.expenseslite.presentation.presenter.transactions;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.transactions.TransactionsGetListImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.transactions.TransactionsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.transactions.TransactionModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionFilterType;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.presenter.ListBasePresenter;
import com.novachevskyi.expenseslite.presentation.view.base.transactions.TransactionsLoadView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joda.time.LocalDate;

public class TransactionsListPresenter extends ListBasePresenter {

  private final TransactionsLoadView transactionsLoadView;

  private final TransactionModelDataMapper transactionModelDataMapper;

  private final TransactionsGetListImpl transactionsGetList;

  private final TransactionFilterType transactionFilterType;

  private LocalDate filterDate;
  private String filterId;

  public TransactionsListPresenter(TransactionsLoadView transactionsLoadView,
      TransactionFilterType transactionFilterType) {
    super(transactionsLoadView);

    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.transactionsGetList = new TransactionsGetListImpl(postExecutionThread);

    this.transactionModelDataMapper = new TransactionModelDataMapper();

    this.transactionsLoadView = transactionsLoadView;

    this.transactionFilterType = transactionFilterType;
  }

  public TransactionsListPresenter(TransactionsLoadView transactionsLoadView,
      TransactionFilterType transactionFilterType, LocalDate filterDate) {
    this(transactionsLoadView, transactionFilterType);

    this.filterDate = filterDate;
  }

  public TransactionsListPresenter(TransactionsLoadView transactionsLoadView,
      TransactionFilterType transactionFilterType, String id) {
    this(transactionsLoadView, transactionFilterType);

    this.filterId = id;
  }

  public void initialize() {
    initializeListPresenter();
    loadData();
  }

  public void loadData() {
    loadListFromScratch();
  }

  @Override
  protected void invokeLoadListMethods(int limit, int skip) {
    this.getTransactionsList(limit, skip);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.transactionsLoadView.getContext(),
        errorBundle.getException());
    this.transactionsLoadView.showError(errorMessage);

    loadingFailed(errorMessage);
  }

  private void transactionsLoaded(TransactionsEntity transactionsEntity) {
    Collection<TransactionModel> collection =
        this.transactionModelDataMapper.transform(transactionsEntity);
    final List<TransactionModel> transactionModelList =
        new ArrayList<>(collection);
    this.transactionsLoadView.transactionsLoaded(transactionModelList, isNeedToUpdateView());

    loadingSucceed(transactionModelList.size());
  }

  private void getTransactionsList(int limit, int skip) {
    if (transactionFilterType == null) {
      this.transactionsGetList.execute(this.transactionsDataCallback, limit, skip);
    } else {
      switch (transactionFilterType) {
        case TODAY:
          this.transactionsGetList.executeToday(this.transactionsDataCallback, limit, skip);
          break;
        case THIS_MONTH:
          this.transactionsGetList.executeThisMonth(this.transactionsDataCallback, limit, skip);
          break;
        case MONTH:
          this.transactionsGetList.executeMonth(this.transactionsDataCallback, limit, skip,
              filterDate);
          break;
        case ACCOUNT:
          this.transactionsGetList.executeAccount(this.transactionsDataCallback, limit, skip,
              filterId);
          break;
        case BUDGET:
          this.transactionsGetList.executeBudget(this.transactionsDataCallback, limit, skip,
              filterId);
          break;
      }
    }
  }

  private final TransactionsDataStore.TransactionsDataCallback transactionsDataCallback =
      new TransactionsDataStore.TransactionsDataCallback() {
        @Override public void onTransactionsLoaded(TransactionsEntity transactionsEntity) {
          TransactionsListPresenter.this.transactionsLoaded(transactionsEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          TransactionsListPresenter.this.showErrorMessage(error);
        }
      };
}
