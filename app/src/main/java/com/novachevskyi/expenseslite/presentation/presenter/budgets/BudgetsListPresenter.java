package com.novachevskyi.expenseslite.presentation.presenter.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetsEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.budgets.BudgetsGetListImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.budgets.BudgetsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.budgets.BudgetModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.presenter.ListBasePresenter;
import com.novachevskyi.expenseslite.presentation.view.base.budgets.BudgetsLoadView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BudgetsListPresenter extends ListBasePresenter {

  private final BudgetsLoadView budgetsLoadView;

  private final BudgetModelDataMapper budgetModelDataMapper;

  private final BudgetsGetListImpl budgetsGetList;

  public BudgetsListPresenter(BudgetsLoadView budgetsLoadView) {
    super(budgetsLoadView);

    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.budgetsGetList = new BudgetsGetListImpl(postExecutionThread);

    this.budgetModelDataMapper = new BudgetModelDataMapper();

    this.budgetsLoadView = budgetsLoadView;
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
    this.getBudgetsList(limit, skip);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.budgetsLoadView.getContext(),
        errorBundle.getException());
    this.budgetsLoadView.showError(errorMessage);

    loadingFailed(errorMessage);
  }

  private void budgetsLoaded(BudgetsEntity budgetsEntity) {
    Collection<BudgetModel> collection =
        this.budgetModelDataMapper.transform(budgetsEntity);
    final List<BudgetModel> budgetModelList =
        new ArrayList<>(collection);
    this.budgetsLoadView.budgetsLoaded(budgetModelList, isNeedToUpdateView());

    loadingSucceed(budgetModelList.size());
  }

  private void getBudgetsList(int limit, int skip) {
    this.budgetsGetList.execute(this.budgetsDataCallback, limit, skip);
  }

  private final BudgetsDataStore.BudgetsDataCallback budgetsDataCallback =
      new BudgetsDataStore.BudgetsDataCallback() {
        @Override public void onBudgetsLoaded(BudgetsEntity budgetsEntity) {
          BudgetsListPresenter.this.budgetsLoaded(budgetsEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          BudgetsListPresenter.this.showErrorMessage(error);
        }
      };
}
