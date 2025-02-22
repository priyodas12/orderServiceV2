package io.springlab.orderService.exception;

public class CustomerDetailsNotFoundException extends RuntimeException {

  public CustomerDetailsNotFoundException(String message) {
    super(message);
  }
}
