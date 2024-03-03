package com.mango.customer.application.exceptions;

import com.mango.customer.domain.exceptions.CustomException;
import com.mango.customer.domain.exceptions.ErrorCode;

public class ApplicationValidationException extends CustomException {

  public ApplicationValidationException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }
}
