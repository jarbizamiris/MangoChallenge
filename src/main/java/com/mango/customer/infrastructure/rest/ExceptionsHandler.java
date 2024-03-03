package com.mango.customer.infrastructure.rest;

import com.mango.customer.application.exceptions.ApplicationNotFoundException;
import com.mango.customer.application.exceptions.ApplicationValidationException;
import com.mango.customer.domain.exceptions.DomainValidationException;
import com.mango.customer.infrastructure.exceptions.InfrastructureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(ApplicationValidationException.class)
  public ErrorResponse handleApplicationValidationException (ApplicationValidationException ex) {
    return new ErrorResponse(ex.getMessage(), ex.getErrorCode());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(DomainValidationException.class)
  public ErrorResponse handleDomainValidationException (DomainValidationException ex) {
    return new ErrorResponse(ex.getMessage(), ex.getErrorCode());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  @ExceptionHandler(ApplicationNotFoundException.class)
  public ErrorResponse handleApplicationNotFoundException (ApplicationNotFoundException ex) {
    return new ErrorResponse(ex.getMessage(), ex.getErrorCode());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(InfrastructureException.class)
  public ErrorResponse handleInfrastructureException (InfrastructureException ex) {
    return new ErrorResponse(ex.getMessage(), ex.getErrorCode());
  }

}
