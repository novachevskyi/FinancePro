package com.novachevskyi.expenseslite.presentation.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.base.LoadListDataView;

public abstract class BaseListFragment extends BaseAdMobFragment implements LoadListDataView {

  @InjectView(R.id.srl_container) SwipeRefreshLayout srl_container;
  @InjectView(R.id.rv_list) RecyclerView rv_list;

  @InjectView(R.id.rl_main) RelativeLayout rl_main;
  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;
  @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
  @InjectView(R.id.rl_empty) RelativeLayout rl_empty;

  @InjectView(R.id.rl_header_stub) RelativeLayout rl_header_stub;

  private ListLayoutManager listLayoutManager;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeList();
    initializeSwipeRefreshLayout();
    initializeRetry();
    initializeViewStubHeader(rl_header_stub);
    initializeEmptyView();

    return fragmentView;
  }

  private void initializeEmptyView() {
    TextView emptyText = (TextView) rl_empty.findViewById(R.id.tv_empty_text);
    emptyText.setText(getEmptyText());
  }

  protected abstract String getEmptyText();

  protected abstract void initializeViewStubHeader(ViewGroup container);

  protected abstract void reloadData();

  protected void setAdapter(RecyclerView.Adapter adapter) {
    this.rv_list.setAdapter(adapter);
  }

  private void initializeRetry() {
    rl_retry.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        reloadData();
      }
    });
  }

  private void initializeSwipeRefreshLayout() {
    srl_container.setColorSchemeColors(getResources().getColor(R.color.pink));
    srl_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        reloadData();
      }
    });
  }

  private void initializeList() {
    listLayoutManager = new ListLayoutManager(getActivity());
    this.rv_list.setLayoutManager(listLayoutManager);

    this.rv_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        BaseListFragment.this.onScrolled(recyclerView, dx, dy, listLayoutManager);
      }
    });
  }

  protected abstract void onScrolled(RecyclerView recyclerView, int dx, int dy,
      ListLayoutManager layoutManager);

  @Override public void showLoading() {
    srl_container.setRefreshing(true);
  }

  @Override public void hideLoading() {
    srl_container.setRefreshing(false);
  }

  @Override public void showMainLayout() {
    rl_main.setVisibility(View.VISIBLE);
  }

  @Override public void hideMainLayout() {
    rl_main.setVisibility(View.GONE);
  }

  @Override public void showLargeProgress() {
    rl_progress.setVisibility(View.VISIBLE);
  }

  @Override public void hideLargeProgress() {
    rl_progress.setVisibility(View.GONE);
  }

  @Override public void showSmallError(String message) {
    showToastMessage(message);
  }

  @Override public void showRetry() {
    rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    rl_retry.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {

  }

  @Override public void dataValidated() {

  }

  @Override public void showEmptyLayout() {
    rl_empty.setVisibility(View.VISIBLE);
  }

  @Override public void hideEmptyLayout() {
    rl_empty.setVisibility(View.GONE);
  }
}
