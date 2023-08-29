package com.lima.hellotodaycore.kafka.consumer;

import com.lima.hellotodaycore.common.config.db.MongoConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import java.util.List;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EPICImagesListener {

  private MongoConnection mongoConnection;

  public EPICImagesListener() {
    this.mongoConnection = BeansUtils.getBean(MongoConnection.class);
  }

  @KafkaListener(topics = "tb_hello_epic_images", groupId = "tb_hello_epic_images_group")
  public void listen(String message) {
    List<Map<String, Object>> deserialize = JsonUtils.deserialize(message, List.class);
    // 가공 하고 싶으면 여기에

    assert deserialize != null;
    for (Map<String, Object> map : deserialize) {
      mongoConnection.insertOne("tb_hello_epic_images", map);
    }
  }
}
