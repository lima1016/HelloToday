package com.lima.hellotodaycore.kafka.consumer;

import com.lima.hellotodaycore.common.config.db.MongoConnection;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

@Slf4j
public class KafkaConsumerConfig {


  // TODO: spark 넣어야해.
  public void run() {
      Properties properties = new Properties();
      properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      properties.put(ConsumerConfig.GROUP_ID_CONFIG, "mid_cast");
      properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest, latest, none

    try(Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
      // 구독할 토픽 설정
      consumer.subscribe(Collections.singletonList("tb_hello_mid"));
      System.out.println("================== consumer start==================== ");
      // 레코드를 지속적으로 폴링
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        // mongo에 저장 해야해.

        records.forEach(record -> {
          List deserialize = JsonUtils.deserialize(record.value(), List.class);
          MongoConnection.getInstance().insertOne("tb_hello_mid",
              (Map<String, Object>) deserialize.get(0));
        });
        if (records.isEmpty()) {
          break;
        }
      }
    } catch (Exception e) {
      log.error("", e);
    }
  }

}
