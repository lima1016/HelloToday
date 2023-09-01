package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.config.db.mongo.MongoCollection;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NeoListener {


  @KafkaListener(topics = "tb_hello_neo_feed", groupId = "tb_hello_neo_feed_group")
  public void listen(String message) {
    MongoCollection mongoCollection = new MongoCollection();
    Map deserialize = JsonUtils.deserialize(message, Map.class);
    mongoCollection.insertOne("tb_hello_neo_feed", deserialize);
  }
}
