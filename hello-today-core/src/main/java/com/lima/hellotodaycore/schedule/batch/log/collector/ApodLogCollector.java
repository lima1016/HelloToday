package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
//import com.lima.hellotodaycore.kafka.consumer.KafkaConsumerConfig;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.JobConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl.Builder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// APOD는 매일 천문학적 관측, 현상, 사진 등에 대한 설명과 함께 이미지나 동영상을 게시
@Slf4j
public class ApodLogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;

//  private final KafkaConsumerConfig kafkaConsumerConfig;

  public ApodLogCollector() {
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = BeansUtils.getBean(KafkaProducerConfig.class);
//    this.kafkaConsumerConfig = BeansUtils.getBean(KafkaConsumerConfig.class);
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "https://api.nasa.gov/planetary/apod";
      Builder builder = connection.buildParameters(url);
      JobConfig.sendHttpResponseToKafka(context, builder, kafkaProducerConfig);
//      kafkaConsumerConfig.run(context.getJobDetail().getJobDataMap().get("topic").toString());
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
