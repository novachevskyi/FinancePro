package com.novachevskyi.expenseslite.domain.executor;

public interface ThreadExecutor {
  void execute(final Runnable runnable);
}
