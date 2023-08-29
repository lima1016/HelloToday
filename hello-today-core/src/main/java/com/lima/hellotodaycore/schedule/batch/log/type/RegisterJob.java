package com.lima.hellotodaycore.schedule.batch.log.type;

import com.lima.hellotodaycore.schedule.batch.log.collector.ApodLogCollector;
import com.lima.hellotodaycore.schedule.batch.log.collector.EPICImagesCollector;
import com.lima.hellotodaycore.schedule.batch.log.collector.NeoLogCollector;
import lombok.Getter;
import org.quartz.Job;

@Getter
public enum RegisterJob {

  APOD(ApodLogCollector.class, "tb_hello_apod", "")
  , NEO_FEED(NeoLogCollector.class, "tb_hello_neo_feed", "")
  , EPIC_IMAGES(EPICImagesCollector.class, "tb_hello_epic_images", "")
  ;

  private final Class<? extends Job> clazz;
  private final String topic;
  private final String time;

  RegisterJob(Class<? extends Job> clazz, String topic, String time) {
    this.clazz = clazz;
    this.topic = topic;
    this.time = time;
  }
}
