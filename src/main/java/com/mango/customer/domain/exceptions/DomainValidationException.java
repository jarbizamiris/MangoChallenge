package com.mango.customer.domain.exceptions;

public class DomainValidationException extends CustomException {

  public DomainValidationException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }
}
