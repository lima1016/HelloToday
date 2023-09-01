package com.lima.hellotodaycore.schedule.batch.log;

import com.lima.hellotodaycore.schedule.batch.log.collector.ApodLogCollector;
import com.lima.hellotodaycore.schedule.batch.log.collector.EPICImagesCollector;
import com.lima.hellotodaycore.schedule.batch.log.collector.NeoLogCollector;
import lombok.Getter;
import org.quartz.Job;

@Getter
public enum RegisterJob {

  APOD(ApodLogCollector.class, "tb_hello_apod", "0 0/1 * * * ?")
  , NEO_FEED(NeoLogCollector.class, "tb_hello_neo_feed", "0 0/1 * * * ?")
  , EPIC_IMAGES(EPICImagesCollector.class, "tb_hello_epic_images", "0 0/1 * * * ?")
  ;

  private final Class<? extends Job> clazz;
  private final String topic;
  private final String scheduleTime;

  RegisterJob(Class<? extends Job> clazz, String topic, String scheduleTime) {
    this.clazz = clazz;
    this.topic = topic;
    this.scheduleTime = scheduleTime;
  }
}
