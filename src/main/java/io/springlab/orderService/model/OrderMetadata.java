package io.springlab.orderService.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderMetadata {

  private BigInteger orderId;
  private OrderStatus orderStatus;
  private Instant orderCreateDate;
  private Instant orderUpdateDate;
  private BigInteger customerId;
  private BigDecimal totalOrderAmount;
  private PaymentMode paymentMode;
}
