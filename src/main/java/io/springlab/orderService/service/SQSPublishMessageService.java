package io.springlab.orderService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.springlab.orderService.exception.SQSPublishMessageException;
import io.springlab.orderService.model.OrderMetadata;
import io.springlab.orderService.model.OrderStatus;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Log4j2
@Service
public class SQSPublishMessageService {

  @Value("${aws.sqs.order-events-queue}")
  private String orderEventsQueueUrl;

  private final SqsClient sqsClient;
  private final ObjectMapper objectMapper;

  public SQSPublishMessageService(SqsClient sqsClient, ObjectMapper objectMapper) {
    this.sqsClient = sqsClient;
    this.objectMapper = objectMapper;
  }

  public SendMessageResponse publishOrderEvent(OrderMetadata orderMetadata) {
    SendMessageResponse messageResponse = null;
    try {
      orderMetadata.setOrderStatus(OrderStatus.PAYMENT_REQUEST);
      String messageBody = objectMapper.writeValueAsString(orderMetadata);

      SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
          .queueUrl(orderEventsQueueUrl)
          .messageBody(messageBody)
          .build();

      messageResponse = sqsClient.sendMessage(sendMessageRequest);
      log.error("published message in sqs: {}", messageResponse.messageId());
    } catch (Exception e) {
      log.error("failed to publish message in sqs: {}", e.getMessage());
      throw new SQSPublishMessageException(e.getMessage());
    }
    return messageResponse;
  }

}
