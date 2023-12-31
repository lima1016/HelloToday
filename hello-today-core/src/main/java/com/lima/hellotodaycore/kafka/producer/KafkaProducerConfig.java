package com.lima.hellotodaycore.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

// singleton 으로 설정해보자
@Configuration
@Slf4j
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String server;

  private static class KafkaProducerSingleton {
    private static final KafkaProducerConfig KAFKA_SINGLETON = new KafkaProducerConfig();
  }

  public static KafkaProducerConfig getInstance() {
    return KafkaProducerSingleton.KAFKA_SINGLETON;
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(properties));
  }

  public void send(String topic, String message) {
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate().send(topic, topic + "_key" , message);
    future.thenAccept(result -> {
      log.info("Message sent successfully: " + result.toString());
    }).exceptionally(ex -> {
      log.info("Failed to send message: " + ex.getMessage());
      return null;
    });
  }
}