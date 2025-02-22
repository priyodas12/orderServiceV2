package io.springlab.orderService.exception;

public class MandatoryFiledValidationException extends RuntimeException {

  public MandatoryFiledValidationException(String message) {
    super(message);
  }
}
