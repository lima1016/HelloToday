package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.db.mongo.MongoExecutor;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentryListener {

  @KafkaListener(topics = "tb_hello_sentry", groupId = "tb_hello_sentry_group")
  public void listen(String message) {
    MongoExecutor mongoExecutor = new MongoExecutor(RegisterJob.SENTRY.getTopic());
    Map deserialize = JsonUtils.deserialize(message, Map.class);
    // 가공 하고 싶으면 여기에

    mongoExecutor.insertOne(deserialize);
  }
}
