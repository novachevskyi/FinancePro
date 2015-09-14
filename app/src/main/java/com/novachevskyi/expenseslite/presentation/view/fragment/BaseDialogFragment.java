package com.novachevskyi.expenseslite.presentation.view.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class BaseDialogFragment extends DialogFragment {

  private static final double WIDTH_SCREEN_PERCENTAGE = 0.95;
  private static final double HEIGHT_SCREEN_PERCENTAGE = 0.75;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    initializePresenter();
  }

  @Override
  public void onStart() {
    super.onStart();

    if (getDialog() == null) {
      return;
    }

    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();

    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    int height = size.y;

    getDialog().getWindow()
        .setLayout((int) (width * WIDTH_SCREEN_PERCENTAGE),
            (int) (height * HEIGHT_SCREEN_PERCENTAGE));
  }

  protected abstract void initializePresenter();

  protected void showToastMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  public Context getContext() {
    return getActivity().getApplicationContext();
  }
}
