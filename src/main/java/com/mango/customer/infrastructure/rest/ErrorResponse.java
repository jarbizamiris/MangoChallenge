package com.mango.customer.infrastructure.rest;

import com.mango.customer.domain.exceptions.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private String message;

  private ErrorCode errorCode;

}
