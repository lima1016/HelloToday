package com.lima.hellotodaycore.schedule.batch.log.type;

import com.lima.hellotodaycore.schedule.batch.log.collector.ApodLogCollector;
import com.lima.hellotodaycore.schedule.batch.log.collector.NeoLogCollector;
import lombok.Getter;
import org.quartz.Job;

@Getter
public enum RegisterJob {

  APOD(ApodLogCollector.class, "tb_log_apod")
  , NEO_FEED(NeoLogCollector.class, "tb_log_neo_feed")
  ;

  private final Class<? extends Job> clazz;
  private final String topic;

  RegisterJob(Class<? extends Job> clazz, String topic) {
    this.clazz = clazz;
    this.topic = topic;
  }
}
