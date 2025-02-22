package io.springlab.orderService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsSQSProperties {

  private String accessKeyId;
  private String secretAccessKey;
  private String region;

}
