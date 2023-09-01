package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.config.db.mongo.MongoCollection;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class APODListener {


  @KafkaListener(topics = "tb_hello_apod", groupId = "tb_hello_apod_group")
  public void listen(String message) {
    MongoCollection mongoCollection = new MongoCollection();
    Map deserialize = JsonUtils.deserialize(message, Map.class);
    // 가공 하고 싶으면 여기에

    mongoCollection.insertOne("tb_hello_apod", deserialize);
  }
}
