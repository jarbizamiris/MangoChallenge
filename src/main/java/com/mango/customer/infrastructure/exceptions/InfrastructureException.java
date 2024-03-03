package com.mango.customer.infrastructure.exceptions;

import com.mango.customer.domain.exceptions.CustomException;
import com.mango.customer.domain.exceptions.ErrorCode;

public class InfrastructureException extends CustomException {

  public InfrastructureException(String message,
      ErrorCode errorCode) {
    super(message, errorCode);
  }
}
