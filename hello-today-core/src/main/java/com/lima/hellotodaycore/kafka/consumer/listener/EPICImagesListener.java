package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.db.mongo.MongoExecutor;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import java.util.List;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EPICImagesListener {

  @KafkaListener(topics = "tb_hello_epic_images", groupId = "tb_hello_epic_images_group")
  public void listen(String message) {
    MongoExecutor mongoExecutor = new MongoExecutor(RegisterJob.EPIC_IMAGES.getTopic());
    List<Map<String, Object>> deserialize = JsonUtils.deserialize(message, List.class);
    // 가공 하고 싶으면 여기에
    // spark로 써서 어떤식으로 가공할지 생각해야함.

    assert deserialize != null;
    for (Map<String, Object> map : deserialize) {
      mongoExecutor.insertOne(map);
    }
  }
}
