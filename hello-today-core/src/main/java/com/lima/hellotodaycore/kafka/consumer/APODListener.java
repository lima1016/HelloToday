package com.lima.hellotodaycore.kafka.consumer;

import com.lima.hellotodaycore.common.config.db.MongoConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class APODListener {

  private MongoConnection mongoConnection;

  public APODListener() {
    this.mongoConnection = BeansUtils.getBean(MongoConnection.class);
  }

  @KafkaListener(topics = "tb_hello_apod", groupId = "tb_hello_apod_group")
  public void listen(String message) {
    Map deserialize = JsonUtils.deserialize(message, Map.class);
    // 가공 하고 싶으면 여기에

    mongoConnection.insertOne("tb_hello_apod", deserialize);
  }
}
