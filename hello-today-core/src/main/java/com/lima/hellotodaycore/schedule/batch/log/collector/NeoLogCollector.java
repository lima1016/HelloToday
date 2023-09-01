package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.common.utils.DateTimeUtils;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.batch.JobConfig;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// 이 정보는 지구에 잠재적인 위험을 가질 수 있는 천체의 특성과 궤도를 파악하는 데 중요

@Slf4j
public class NeoLogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;

  public NeoLogCollector() {
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = RegisterBeans.kafkaProducerBean();
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-08-19&end_date=2023-08-21&detailed=false";
      String startDate = LocalDate.now().toString();
      String fromDate = DateTimeUtils.calculateFromDate(startDate, 1);

      HttpUrl.Builder builder = connection.buildParameters(url);
      builder.addQueryParameter("start_date", startDate);
      builder.addQueryParameter("end_date", fromDate);
      builder.addQueryParameter("detailed", "false");

      JobConfig.sendHttpResponseToKafka(context, builder, kafkaProducerConfig);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
