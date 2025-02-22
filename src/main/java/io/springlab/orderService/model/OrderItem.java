package io.springlab.orderService.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderItem {

  private Long orderItemId;
  private String globalOrderIdentifier;
  private BigDecimal orderAmountPerItem;
  private Long orderQuantity;

}
