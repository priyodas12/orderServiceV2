package io.springlab.orderService.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "orders")
@Data
@Builder
public class Order {

  @Id
  private BigInteger orderId;
  private OrderStatus orderStatus;
  List<OrderItem> orderItemList;
  private Instant orderCreateDate;
  private Instant orderUpdateDate;
  private BigInteger customerId;
  private BigDecimal totalOrderAmount;
}
