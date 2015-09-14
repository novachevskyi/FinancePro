package com.novachevskyi.expenseslite.presentation.presenter.accounts;

import com.novachevskyi.expenseslite.data.models.accounts.AccountsEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.accounts.AccountsGetListImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.accounts.AccountsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.accounts.AccountModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.presenter.ListBasePresenter;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountsLoadView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountsListPresenter extends ListBasePresenter {

  private final AccountsLoadView accountsLoadView;

  private final AccountModelDataMapper accountModelDataMapper;

  private final AccountsGetListImpl accountsGetList;

  public AccountsListPresenter(AccountsLoadView accountsLoadView) {
    super(accountsLoadView);

    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.accountsGetList = new AccountsGetListImpl(postExecutionThread);

    this.accountModelDataMapper = new AccountModelDataMapper();

    this.accountsLoadView = accountsLoadView;
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
    this.getAccountsList(limit, skip);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.accountsLoadView.getContext(),
        errorBundle.getException());
    this.accountsLoadView.showError(errorMessage);

    loadingFailed(errorMessage);
  }

  private void accountsLoaded(AccountsEntity accountsEntity) {
    Collection<AccountModel> collection = this.accountModelDataMapper.transform(accountsEntity);
    final List<AccountModel> accountModelList =
        new ArrayList<>(collection);
    this.accountsLoadView.accountsLoaded(accountModelList, isNeedToUpdateView());

    loadingSucceed(accountModelList.size());
  }

  private void getAccountsList(int limit, int skip) {
    this.accountsGetList.execute(this.accountsDataCallback, limit, skip);
  }

  private final AccountsDataStore.AccountsDataCallback accountsDataCallback =
      new AccountsDataStore.AccountsDataCallback() {
        @Override public void onAccountsLoaded(AccountsEntity accountEntities) {
          AccountsListPresenter.this.accountsLoaded(accountEntities);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AccountsListPresenter.this.showErrorMessage(error);
        }
      };
}
