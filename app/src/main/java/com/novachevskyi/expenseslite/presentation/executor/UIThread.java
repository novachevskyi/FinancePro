package com.novachevskyi.expenseslite.presentation.executor;

import android.os.Handler;
import android.os.Looper;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;

public class UIThread implements PostExecutionThread {

  private static class LazyHolder {
    private static final UIThread instance = new UIThread();
  }

  public static UIThread getInstance() {
    return LazyHolder.instance;
  }

  private final Handler handler;

  private UIThread() {
    this.handler = new Handler(Looper.getMainLooper());
  }

  @Override public void post(Runnable runnable) {
    handler.post(runnable);
  }
}
