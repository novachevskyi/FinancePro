package com.novachevskyi.expenseslite.domain.exception;

public class RepositoryErrorBundle implements ErrorBundle {

  private final Exception exception;

  public RepositoryErrorBundle(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

  public String getErrorMessage() {
    String message = "";
    if (exception != null) {
      exception.getMessage();
    }
    return message;
  }
}
