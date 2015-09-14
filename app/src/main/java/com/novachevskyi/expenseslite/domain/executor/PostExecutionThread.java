package com.novachevskyi.expenseslite.domain.executor;

public interface PostExecutionThread {
  void post(Runnable runnable);
}
