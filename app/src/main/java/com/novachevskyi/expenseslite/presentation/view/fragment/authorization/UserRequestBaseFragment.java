package com.novachevskyi.expenseslite.presentation.view.fragment.authorization;

import com.novachevskyi.expenseslite.presentation.view.fragment.BaseFragment;

public abstract class UserRequestBaseFragment extends BaseFragment {

  private OnCurrentUserRequestListener onCurrentUserRequestListener;

  public void setOnCurrentUserRequestListener(
      OnCurrentUserRequestListener onCurrentUserRequestListener) {
    this.onCurrentUserRequestListener = onCurrentUserRequestListener;
  }

  public interface OnCurrentUserRequestListener {
    void onUserLoadSucceed();

    void onUserLoadFailed();
  }

  protected void notifyUserLoadSucceed() {
    if (onCurrentUserRequestListener != null) {
      onCurrentUserRequestListener.onUserLoadSucceed();
    }
  }

  protected void notifyUserLoadFailed() {
    if (onCurrentUserRequestListener != null) {
      onCurrentUserRequestListener.onUserLoadFailed();
    }
  }
}
