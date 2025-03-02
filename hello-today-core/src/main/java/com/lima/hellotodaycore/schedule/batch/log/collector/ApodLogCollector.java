package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.lima.hellotodaycore.common.config.connection.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.batch.JobConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl.Builder;
import okhttp3.OkHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// APOD는 매일 천문학적 관측, 현상, 사진 등에 대한 설명과 함께 이미지나 동영상을 게시
@Slf4j
public class ApodLogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;


  public ApodLogCollector() {
    OkHttpClient instance = OkHttpClientConnection.getInstance();
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = RegisterBeans.kafkaProducerBean();
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "https://api.nasa.gov/planetary/apod";
      Builder builder = connection.buildParameters(url);
      JobConfig.sendHttpResponseToKafka(context, builder, kafkaProducerConfig);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
