package com.lima.hellotodaycore.kafka.consumer;

import com.lima.hellotodaycore.common.config.db.MongoConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NeoListener {

  private MongoConnection mongoConnection;

  public NeoListener() {
    this.mongoConnection = BeansUtils.getBean(MongoConnection.class);
  }

  @KafkaListener(topics = "tb_hello_neo_feed", groupId = "tb_hello_neo_feed_group")
  public void listen(String message) {
    Map deserialize = JsonUtils.deserialize(message, Map.class);
    MongoConnection.getInstance().insertOne("tb_hello_neo_feed", deserialize);
  }
}
