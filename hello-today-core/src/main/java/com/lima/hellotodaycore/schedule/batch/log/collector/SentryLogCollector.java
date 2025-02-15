package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.lima.hellotodaycore.common.config.connection.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.batch.JobConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl.Builder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// 지구에 접근할 수 있는 천체(Near-Earth Objects, NEOs)에 관한 정보
@Slf4j
public class SentryLogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;


  public SentryLogCollector() {
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = RegisterBeans.kafkaProducerBean();
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "https://ssd-api.jpl.nasa.gov/sentry.api";
      Builder builder = connection.buildParameters(url);
      JobConfig.sendHttpResponseToKafka(context, builder, kafkaProducerConfig);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
