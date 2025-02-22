package io.springlab.orderService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SQSClientConfig {

  private final AwsSQSProperties awsSQSProperties;

  public SQSClientConfig(AwsSQSProperties awsSQSProperties) {
    this.awsSQSProperties = awsSQSProperties;
  }

  @Bean
  public SqsClient sqsClient() {

    AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(
        AwsBasicCredentials.create(awsSQSProperties.getAccessKeyId(),
            awsSQSProperties.getSecretAccessKey()));

    return SqsClient.builder()
        .region(Region.AP_SOUTH_1)
        .credentialsProvider(awsCredentialsProvider)
        .build();
  }
}

