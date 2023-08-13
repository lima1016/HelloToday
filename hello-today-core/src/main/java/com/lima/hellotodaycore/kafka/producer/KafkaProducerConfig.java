package com.lima.hellotodaycore.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String server;

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate () {
    Map<String, Object> properties = new HashMap<>();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    // 메시지의 키와 값의 직렬화 방식을 지정하는 속성. 카프카에 메시지를 보낼 때 메시지의 키와 값을 직렬화하여 바이트 형태로 변환해야함.
    /*
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG:
    이 속성은 메시지의 키를 어떤 방식으로 직렬화할지를 지정합니다.
    카프카 메시지의 키는 주로 파티셔닝과 관련된 역할을 하므로, 키의 직렬화 방식을 지정해야 합니다.
    StringSerializer 클래스는 문자열을 직렬화하는 데 사용되며, 메시지의 키가 문자열로 되어 있다면 이 클래스를 사용하여 직렬화합니다.
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG:
    이 속성은 메시지의 값을 어떤 방식으로 직렬화할지를 지정합니다.
    메시지의 값은 실제 데이터를 나타내므로, 데이터의 형식에 따라 적절한 직렬화 방식을 선택해야 합니다.
    StringSerializer 클래스는 문자열을 직렬화하는 데 사용되며, 메시지의 값이 문자열로 되어 있다면 이 클래스를 사용하여 직렬화합니다.
     */
//    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(properties));
  }

  public void send(String topic, String message) {
    kafkaTemplate().send(topic, message);
  }
}