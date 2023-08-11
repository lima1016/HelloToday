package com.lima.hellotodaycore.kafka.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.security.core.parameters.P;

public class KafkaProducerConfig {

  public void config () {
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    // init kafka producer
    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

    // int idx = 0;
    while (true) {
      String topic = "";
//      Integer partition = 0; // partition number (default: Round Robin)
//      String key = "key-" + idx // key (default: null)
//      String value = "record-" + idx // value

      ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, "", "");
      producer.send(record);
      /*
      producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception != null) {
                            exception.printStackTrace();
                        } else {
                            System.out.println("Message sent successfully to topic: " + metadata.topic());
                            System.out.println("Partition: " + metadata.partition());
                            System.out.println("Offset: " + metadata.offset());
                        }
                    }
                });
       */
    }
  }
}