package com.novachevskyi.expenseslite.presentation.view.fragment.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.users.UserModel;
import com.novachevskyi.expenseslite.presentation.presenter.authorization.SplashPresenter;
import com.novachevskyi.expenseslite.presentation.view.base.authorization.UserLoadView;

public class SplashFragment extends UserRequestBaseFragment implements UserLoadView {

  private SplashPresenter splashPresenter;

  @InjectView(R.id.rl_progress) RelativeLayout rl_progress;

  public static SplashFragment newInstance() {
    SplashFragment splashFragment = new SplashFragment();

    Bundle argumentsBundle = new Bundle();
    splashFragment.setArguments(argumentsBundle);

    return splashFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_splash, container, false);
    ButterKnife.inject(this, fragmentView);

    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.splashPresenter.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    this.splashPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.splashPresenter.pause();
  }

  @Override protected void initializePresenter() {
    this.splashPresenter =
        new SplashPresenter(this);
  }

  @Override public void userLoaded(UserModel user) {
    notifyUserLoadSucceed();
  }

  @Override public void showError(String message) {
    notifyUserLoadFailed();
  }

  @Override public void dataValidated() {

  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
  }

  @Override public void showRetry() {
  }

  @Override public void hideRetry() {
  }
}
