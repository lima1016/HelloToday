package com.lima.hellotodaycore.common.config;

import com.lima.hellotodaycore.common.db.mongo.MongoConnection;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RegisterBeans {

  @Bean
  public static MongoDatabase mongoConnectionBean() {
    return MongoConnection.getInstance();
  }

  @Bean
  public static KafkaProducerConfig kafkaProducerBean() {
    return KafkaProducerConfig.getInstance();
  }
}
