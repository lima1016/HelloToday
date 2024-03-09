package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.db.opensearch.OpenSearchCreating;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TLEListener {

  @KafkaListener(topics = "tb_hello_tle", groupId = "tb_hello_tle_group")
  public void listen(String message) {
    OpenSearchCreating searching = new OpenSearchCreating(RegisterJob.TLE.getTopic());
    searching.createData(message);
  }
}
