package com.lima.hellotodaycore.kafka.consumer;

import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String server;

  // TODO: spark 넣어야해.
  public void run(String topic) {
    log.info("================== consumer " + topic + " start====================");
    String groupId = topic + "_group";
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest, latest, none

    try(Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
      // 구독할 토픽 설정
      consumer.subscribe(Collections.singletonList(topic));
      // 레코드를 지속적으로 폴링
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));

        records.forEach(record -> {
          List deserialize = JsonUtils.deserialize(record.value(), List.class);

//          MongoConnection.getInstance().insertOne(topic,
//              (Map<String, Object>) deserialize.get(0));
        });

      }

    } catch (Exception e) {
      log.error("", e);
    }
  }
}
