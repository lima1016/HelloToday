package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.batch.JobConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl.Builder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// TLE (Two-Line Element Set) 형식으로 주어진 위성의 궤도 정보
@Slf4j
public class TLELogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;


  public TLELogCollector() {
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = RegisterBeans.kafkaProducerBean();
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "https://tle.ivanstanojevic.me/api/tle";
      Builder builder = connection.buildUrl(url);
      JobConfig.sendHttpResponseToKafka(context, builder, kafkaProducerConfig);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
