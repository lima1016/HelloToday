package com.lima.hellotodaycore.kafka.consumer.listener;

import com.lima.hellotodaycore.common.db.opensearch.OpenSearchCreating;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentryListener {

  @KafkaListener(topics = "tb_hello_sentry", groupId = "tb_hello_sentry_group")
  public void listen(String message) {
    OpenSearchCreating searching = new OpenSearchCreating(RegisterJob.SENTRY.getTopic());
    searching.createData(message);
  }
}
