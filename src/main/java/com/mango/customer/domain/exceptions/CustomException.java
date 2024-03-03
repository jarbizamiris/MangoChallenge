package com.mango.customer.domain.exceptions;

// me parecio mucho tener el data solo por el get del errorCode
public class CustomException extends RuntimeException {

  private ErrorCode errorCode;

  public CustomException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
      return errorCode;
  }
}
