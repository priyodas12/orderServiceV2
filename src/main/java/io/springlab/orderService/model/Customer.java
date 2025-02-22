package io.springlab.orderService.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "customers")
@Data
@Builder
public class Customer {

  @Id
  private BigInteger customerId;
  private String customerName;
  private String customerEmailId;

}
