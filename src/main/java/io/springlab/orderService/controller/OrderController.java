package io.springlab.orderService.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.springlab.orderService.model.Order;
import io.springlab.orderService.model.OrderMetadata;
import io.springlab.orderService.service.OrderService;
import io.springlab.orderService.service.SQSPublishMessageService;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Log4j2
@RequestMapping("/api/v2")
@RestController
public class OrderController {

  private final OrderService orderService;

  private final SQSPublishMessageService sqsPublishMessageService;

  public OrderController(OrderService orderService,
      SQSPublishMessageService sqsPublishMessageService) {
    this.orderService = orderService;
    this.sqsPublishMessageService = sqsPublishMessageService;
  }

  @PostMapping("/orders")
  public ResponseEntity<Order> createOrder(@RequestBody Order order,
      @RequestParam("paymentMode") String paymentMode) {
    Order createdOrder = orderService.createOrder(order);
    OrderMetadata orderMetadata = orderService.prepareOrderMetaData(createdOrder, paymentMode);
    SendMessageResponse sqsResponse = sqsPublishMessageService.publishOrderEvent(orderMetadata);
    log.info("Published message into queue: body: {} , messageId: {}",
        sqsResponse.md5OfMessageBody(), sqsResponse.messageId());
    return ResponseEntity.status(201).body(createdOrder);
  }

  @GetMapping("/orders/{orderId}")
  public ResponseEntity<Order> getOrderById(@PathVariable("orderId") String orderId) {
    return ResponseEntity.status(200).body(orderService.getOrderById(BigInteger.valueOf(
        Long.parseLong(orderId))));
  }

  @GetMapping("/orders")
  public ResponseEntity<List<Order>> getOrders() {
    return ResponseEntity.status(200).body(orderService.getOrders());
  }
}
