package io.springlab.orderService.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.springlab.orderService.dao.CustomerDao;
import io.springlab.orderService.dao.OrderDao;
import io.springlab.orderService.exception.CustomerDetailsNotFoundException;
import io.springlab.orderService.exception.MandatoryFiledValidationException;
import io.springlab.orderService.model.Order;
import io.springlab.orderService.model.OrderItem;
import io.springlab.orderService.model.OrderMetadata;
import io.springlab.orderService.model.OrderStatus;
import io.springlab.orderService.model.PaymentMode;

@Service
public class OrderService {

  private final OrderDao orderDao;

  private final CustomerDao customerDao;

  public OrderService(OrderDao orderDao, CustomerDao customerDao) {
    this.orderDao = orderDao;
    this.customerDao = customerDao;
  }

  public Order createOrder(Order order) {
    if (Optional.ofNullable(order.getCustomerId()).isEmpty()) {
      throw new MandatoryFiledValidationException("Exception: Customer Id Mandatory");
    }
    if (customerDao.findById(order.getCustomerId()).isEmpty()) {
      throw new CustomerDetailsNotFoundException("Exception: Customer Does Not Exist!");
    }
    order.setOrderId(BigInteger.valueOf(Instant.now().toEpochMilli()));
    order.setOrderCreateDate(Instant.now());
    order.setOrderUpdateDate(Instant.now());
    order.setOrderStatus(OrderStatus.PENDING);
    order.getOrderItemList()
        .forEach(orderItem -> orderItem.setGlobalOrderIdentifier(UUID.randomUUID().toString()));
    order.setTotalOrderAmount(calculateTotalOrderAmount(order.getOrderItemList()));
    return orderDao.save(order);
  }

  public Order getOrderById(BigInteger orderId) {
    return orderDao.findById(orderId).orElse(null);
  }

  public List<Order> getOrders() {
    return orderDao.findAll().stream().toList();
  }

  private BigDecimal calculateTotalOrderAmount(List<OrderItem> orderItemList) {
    return orderItemList.stream().map(orderItem -> orderItem.getOrderAmountPerItem().multiply(
            BigDecimal.valueOf(orderItem.getOrderQuantity()))).toList().stream().reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }

  public OrderMetadata prepareOrderMetaData(Order order, String paymentMode) {
    return OrderMetadata.builder()
        .orderId(order.getOrderId())
        .orderStatus(OrderStatus.PAYMENT_REQUEST)
        .orderCreateDate(order.getOrderCreateDate())
        .orderUpdateDate(Instant.now())
        .customerId(order.getCustomerId())
        .totalOrderAmount(order.getTotalOrderAmount())
        .paymentMode(PaymentMode.valueOf(paymentMode))
        .build();
  }
}
