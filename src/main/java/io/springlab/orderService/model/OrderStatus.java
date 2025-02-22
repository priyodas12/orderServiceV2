package io.springlab.orderService.model;

public enum OrderStatus {
  PENDING,
  PAYMENT_REQUEST,
  PAYMENT_SUCCESS,
  PAYMENT_FAILED,
  CANCELED
}
