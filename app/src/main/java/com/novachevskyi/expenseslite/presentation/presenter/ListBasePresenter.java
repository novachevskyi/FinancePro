package com.novachevskyi.expenseslite.presentation.presenter;

import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.base.LoadListDataView;

public abstract class ListBasePresenter implements Presenter {

  private static final int LOADING_STEP = 20;
  private static final int LOAD_MORE_ITEMS_START_DELTA = 4;

  private boolean isLoading;
  private boolean isAllItemsLoaded;
  private boolean isInitialLoad;
  private boolean isNeedToUpdateView;

  private LoadListDataView loadListDataView;

  private boolean isPagingDisabled;

  private boolean isNeedToUpdate;

  protected ListBasePresenter(LoadListDataView loadListDataView) {
    this.loadListDataView = loadListDataView;
  }

  @Override public void resume() {
    if(isNeedToUpdate) {
      loadListFromScratch();
      isNeedToUpdate = false;
    }
  }

  @Override public void pause() {
    isNeedToUpdate = true;
  }

  protected void loadListFromScratch() {
    if (!this.isLoading) {
      this.isNeedToUpdateView = true;
      this.isAllItemsLoaded = false;

      invokeLoad(0);
    }
  }

  protected void loadMoreItems(int count) {
    if (!this.isLoading && !this.isAllItemsLoaded) {
      invokeLoad(count);
    }
  }

  private void invokeLoad(int skip) {
    this.isLoading = true;
    showViewLoading();
    invokeLoadListMethods(LOADING_STEP, skip);
  }

  protected abstract void invokeLoadListMethods(int limit, int skip);

  protected void loadingSucceed(int itemsSize) {
    this.isLoading = false;

    if(isInitialLoad && itemsSize <= 0) {
      showEmptyView();
      hideMainView();
    } else {
      showMainView();
      hideEmptyView();
    }

    if (itemsSize <= 0) {
      this.isAllItemsLoaded = true;
    } else {
      this.isInitialLoad = false;
      this.isNeedToUpdateView = false;
    }

    hideSmallViewLoading();
    hideLargeViewLoading();
    hideLargeViewRetry();
  }

  protected void loadingFailed(String message) {
    this.isLoading = false;

    if (isInitialLoad()) {
      showLargeViewRetry();
      hideMainView();
    } else {
      showSmallError(message);
    }

    hideSmallViewLoading();
    hideLargeViewLoading();
  }

  private boolean isInitialLoad() {
    return isInitialLoad;
  }

  protected boolean isNeedToUpdateView() {
    return isNeedToUpdateView;
  }

  public void listScrolled(ListLayoutManager accountsLayoutManager) {
    if (!isPagingDisabled) {
      int visibleItemCount = accountsLayoutManager.getChildCount();
      int totalItemCount = accountsLayoutManager.getItemCount();
      int pastVisibleItems = accountsLayoutManager.findFirstVisibleItemPosition();

      if ((visibleItemCount + pastVisibleItems) >= totalItemCount - LOAD_MORE_ITEMS_START_DELTA) {
        loadMoreItems(totalItemCount);
      }
    }
  }

  protected void initializeListPresenter() {
    isInitialLoad = true;
    isNeedToUpdateView = true;
  }

  private void showViewLoading() {
    if (isInitialLoad()) {
      showLargeViewLoading();
      hideMainView();
    } else {
      showSmallViewLoading();
    }
  }

  private void showSmallViewLoading() {
    this.loadListDataView.showLoading();
  }

  private void hideSmallViewLoading() {
    this.loadListDataView.hideLoading();
  }

  private void showLargeViewLoading() {
    this.loadListDataView.showLargeProgress();
  }

  private void hideLargeViewLoading() {
    this.loadListDataView.hideLargeProgress();
  }

  private void hideLargeViewRetry() {
    this.loadListDataView.hideRetry();
  }

  private void showLargeViewRetry() {
    this.loadListDataView.showRetry();
  }

  private void hideMainView() {
    this.loadListDataView.hideMainLayout();
  }

  private void showSmallError(String message) {
    this.loadListDataView.showSmallError(message);
  }

  private void showMainView() {
    this.loadListDataView.showMainLayout();
  }

  private void hideEmptyView() {
    this.loadListDataView.hideEmptyLayout();
  }

  private void showEmptyView() {
    this.loadListDataView.showEmptyLayout();
  }

  public void setPagingDisabled(boolean isPagingDisabled) {
    this.isPagingDisabled = isPagingDisabled;
  }
}
