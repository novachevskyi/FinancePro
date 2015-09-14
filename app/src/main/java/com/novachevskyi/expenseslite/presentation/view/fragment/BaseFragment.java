package com.novachevskyi.expenseslite.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import android.widget.Toast;
import com.novachevskyi.expenseslite.presentation.navigation.Navigator;
import com.novachevskyi.expenseslite.presentation.view.component.FeedbackController;

public abstract class BaseFragment extends Fragment {

  private FeedbackController feedbackController;

  private Navigator navigator;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    initializePresenter();
    initializeFeedbackController();
    initializeNavigator();
  }

  private void initializeNavigator() {
    navigator = new Navigator();
  }

  @Override public void onResume() {
    super.onResume();
    feedbackController.start();
  }

  @Override public void onPause() {
    super.onPause();
    feedbackController.stop();
  }

  public void tryVibrate() {
    feedbackController.tryVibrate();
  }

  private void initializeFeedbackController() {
    feedbackController = new FeedbackController(getContext());
  }

  protected abstract void initializePresenter();

  protected void showToastMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  protected void hideKeyboard() {
    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
  }

  public Context getContext() {
    return getActivity().getApplicationContext();
  }

  protected Navigator getNavigator() {
    return navigator;
  }
}
