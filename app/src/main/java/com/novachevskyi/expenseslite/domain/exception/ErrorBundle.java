package com.novachevskyi.expenseslite.domain.exception;

public interface ErrorBundle {
  Exception getException();

  String getErrorMessage();
}
