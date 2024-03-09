package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.db.opensearch.OpenSearchCreating;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NeoListener {

  @KafkaListener(topics = "tb_hello_neo_feed", groupId = "tb_hello_neo_feed_group")
  public void listen(String message) {
    OpenSearchCreating searching = new OpenSearchCreating(RegisterJob.NEO_FEED.getTopic());
    searching.createData(message);
  }
}
