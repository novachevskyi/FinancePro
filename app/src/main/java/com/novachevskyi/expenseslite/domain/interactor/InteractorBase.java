package com.novachevskyi.expenseslite.domain.interactor;

import com.novachevskyi.expenseslite.domain.executor.JobExecutor;
import com.novachevskyi.expenseslite.domain.executor.PostExecutionThread;
import com.novachevskyi.expenseslite.domain.executor.ThreadExecutor;
import com.novachevskyi.expenseslite.domain.repository.datasource.RepositoryCallback;

public abstract class InteractorBase implements Interactor {

  private final ThreadExecutor threadExecutor;
  protected final PostExecutionThread postExecutionThread;

  protected RepositoryCallback callback;

  public InteractorBase(PostExecutionThread postExecutionThread) {
    this.postExecutionThread = postExecutionThread;
    this.threadExecutor = JobExecutor.getInstance();
  }

  protected void executeBase(RepositoryCallback callback) {
    this.callback = callback;
    this.threadExecutor.execute(this);
  }
}
