package com.lima.hellotodaycore.kafka.consumer;

import com.lima.hellotodaycore.common.config.db.MongoConnection;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class APODListener {

  @KafkaListener(topics = "tb_hello_apod", groupId = "tb_hello_apod_group")
  public void listen(String message) {
    Map deserialize = JsonUtils.deserialize(message, Map.class);
    MongoConnection.getInstance().insertOne("tb_hello_apod", deserialize);
  }
}
