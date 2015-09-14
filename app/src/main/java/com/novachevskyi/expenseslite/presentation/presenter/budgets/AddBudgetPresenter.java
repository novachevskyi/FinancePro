package com.novachevskyi.expenseslite.presentation.presenter.budgets;

import com.novachevskyi.expenseslite.data.models.budgets.BudgetEntity;
import com.novachevskyi.expenseslite.domain.exception.ErrorBundle;
import com.novachevskyi.expenseslite.domain.exception.RepositoryErrorBundle;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.interactor.budgets.BudgetsCreateImpl;
import com.novachevskyi.expenseslite.domain.repository.datasource.budgets.BudgetsDataStore;
import com.novachevskyi.expenseslite.presentation.exception.ErrorMessageFactory;
import com.novachevskyi.expenseslite.presentation.executor.UIThread;
import com.novachevskyi.expenseslite.presentation.mapper.budgets.BudgetModelDataMapper;
import com.novachevskyi.expenseslite.presentation.model.budgets.BudgetModel;
import com.novachevskyi.expenseslite.presentation.presenter.Presenter;
import com.novachevskyi.expenseslite.presentation.utils.Validator;
import com.novachevskyi.expenseslite.presentation.view.base.budgets.BudgetLoadView;

public class AddBudgetPresenter implements Presenter {

  private final BudgetLoadView budgetLoadView;
  private final BudgetsCreateImpl budgetsCreate;
  private final BudgetModelDataMapper budgetModelDataMapper;

  private BudgetEntity budgetEntity;

  public AddBudgetPresenter(BudgetLoadView budgetLoadView) {
    PostExecutionThread postExecutionThread = UIThread.getInstance();
    this.budgetsCreate = new BudgetsCreateImpl(postExecutionThread);
    this.budgetModelDataMapper = new BudgetModelDataMapper();
    this.budgetLoadView = budgetLoadView;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  public void initialize(int category, String dateFrom,
      String dateTo, double amount, double alertAmount, String notes) {
    this.createNewBudget(category, dateFrom, dateTo,
        amount, alertAmount, notes);
  }

  private void createNewBudget(int category, String dateFrom,
      String dateTo, double amount, double alertAmount, String notes) {
    this.showViewLoading();

    budgetEntity = new BudgetEntity(category, dateFrom, dateTo,
        amount, alertAmount, notes);
    this.createBudget(budgetEntity);
  }

  private void showViewLoading() {
    this.budgetLoadView.showLoading();
  }

  private void hideViewLoading() {
    this.budgetLoadView.hideLoading();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.budgetLoadView.getContext(),
        errorBundle.getException());
    this.budgetLoadView.showError(errorMessage);
  }

  private void budgetLoaded(BudgetEntity budgetEntity) {
    if (budgetEntity != null && this.budgetEntity != null) {
      this.budgetEntity.objectId = budgetEntity.objectId;
    }
    final BudgetModel budgetModel =
        this.budgetModelDataMapper.transform(this.budgetEntity);
    this.budgetLoadView.budgetLoaded(budgetModel);
  }

  private void createBudget(BudgetEntity budgetEntity) {
    this.budgetsCreate.execute(this.budgetDataCallback, budgetEntity);
  }

  private final BudgetsDataStore.BudgetDataCallback budgetDataCallback =
      new BudgetsDataStore.BudgetDataCallback() {
        @Override public void onBudgetLoaded(BudgetEntity budgetEntity) {
          AddBudgetPresenter.this.hideViewLoading();
          AddBudgetPresenter.this.budgetLoaded(budgetEntity);
        }

        @Override public void onError(RepositoryErrorBundle error) {
          AddBudgetPresenter.this.hideViewLoading();
          AddBudgetPresenter.this.showErrorMessage(error);
        }
      };

  public boolean checkAmount(String title) {
    String error = Validator.validateAmount(budgetLoadView.getContext(), title);

    if (error == null) {
      budgetLoadView.amountDataAccepted();
      return true;
    } else {
      budgetLoadView.amountDataError(error);
      return false;
    }
  }

  public void validateData(String amount) {
    if (checkAmount(amount)) {
      budgetLoadView.dataValidated();
    }
  }
}
