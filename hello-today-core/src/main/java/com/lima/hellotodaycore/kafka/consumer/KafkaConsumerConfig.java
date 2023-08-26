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

      // LIM: 추가된 부분
      // 자동 Offset Commit(enable.auto.commit=true) 대신 수동으로 Offset을 Commit
//        for (ConsumerRecord<String, String> record : records) {
//           메시지 처리 로직
//          consumer.commitSync(Collections.singletonMap(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));
//        }

      // Consumer가 메시지를 가져오면, 그 메시지의 Offset 정보를 로그로 남김
//        for (ConsumerRecord<String, String> record : records) {
//          log.info("Consumed record with key {} and offset {}", record.key(), record.offset());
//        }

      // ConsumerRebalanceListener: 리밸런스가 발생할 때 호출되는 리스너를 추가하여, 리밸런스 이벤트를 로그로 남길 수 있음
//        consumer.subscribe(Collections.singleton(topic), new ConsumerRebalanceListener() {
//          @Override
//          public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//            log.info("Partitions revoked: {}", partitions);
//          }
//
//          @Override
//          public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
//            log.info("Partitions assigned: {}", partitions);
//          }
//        });
    } catch (Exception e) {
      log.error("", e);
    }
  }

}
