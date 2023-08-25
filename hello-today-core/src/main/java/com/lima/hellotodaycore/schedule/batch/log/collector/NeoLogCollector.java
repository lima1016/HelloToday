package com.lima.hellotodaycore.schedule.batch.log.collector;


import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.HelloDateUtils;
import com.lima.hellotodaycore.kafka.consumer.KafkaConsumerConfig;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.JobConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
public class NeoLogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;

  public NeoLogCollector(OkHttpClientConnection connection, KafkaProducerConfig kafkaProducerConfig) {
    this.connection = connection;
    this.kafkaProducerConfig = kafkaProducerConfig;
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      String url = "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-08-19&end_date=2023-08-21&detailed=false";
      String startDate = LocalDate.now().toString();
      String fromDate = HelloDateUtils.calculateFromDate(startDate, 1);

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
