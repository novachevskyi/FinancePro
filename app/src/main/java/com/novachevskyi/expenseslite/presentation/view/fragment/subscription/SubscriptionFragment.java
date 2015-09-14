package com.novachevskyi.expenseslite.presentation.view.fragment.subscription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseFragment;

public class SubscriptionFragment extends BaseFragment {

  public static SubscriptionFragment newInstance() {
    SubscriptionFragment subscriptionFragment = new SubscriptionFragment();

    Bundle argumentsBundle = new Bundle();
    subscriptionFragment.setArguments(argumentsBundle);

    return subscriptionFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initialize();
  }

  private void initialize() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_subscription, container, false);
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override protected void initializePresenter() {

  }

  @OnClick(R.id.btn_subscribe) void onButtonSubscribeClick() {
    SubscriptionFragment.this.subscribe();
  }

  private void subscribe() {
    if(onSubscribeListener != null) {
      onSubscribeListener.onSubscribe();
    }
  }

  private OnSubscribeListener onSubscribeListener;

  public void setOnSubscribeListener(
      OnSubscribeListener onSubscribeListener) {
    this.onSubscribeListener = onSubscribeListener;
  }

  public interface OnSubscribeListener {
    void onSubscribe();
  }
}
