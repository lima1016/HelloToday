package com.lima.hellotodaycore.schedule;

import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.batch.log.type.RegisterJob;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@Slf4j
public class JobConfig {

  @Bean
  public List<JobDetail> jobDetail() {
    List<JobDetail> jobDetailList = new ArrayList<>();

    for (RegisterJob value : RegisterJob.values()) {
      JobDetailFactoryBean jobFactory = new JobDetailFactoryBean();
      Map<String, Object> data = new HashMap<>();

      jobFactory.setJobClass(value.getClazz());
      jobFactory.setName(value.name());
      data.put("topic", value.getTopic());
      jobFactory.setJobDataAsMap(data);
      jobFactory.afterPropertiesSet();
      jobDetailList.add(jobFactory.getObject());
    }
    return jobDetailList;
  }

  @Bean
  List<CronTrigger> cronTrigger(List<JobDetail> jobDetails) {
    try {
      List<CronTrigger> triggers = new ArrayList<>();

      for (JobDetail jobDetail : jobDetails) {
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        triggerFactory.setJobDetail(jobDetail);
        triggerFactory.setCronExpression("0 0/1 * * * ?"); // 매일 오전 6시마다 0 0/5 * * * ? => 매 5분 마다
        triggerFactory.afterPropertiesSet();
        triggers.add(triggerFactory.getObject());
      }

      return triggers;
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  public SchedulerFactoryBean schedulerFactory(List<CronTrigger> triggers) {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setTriggers(triggers.toArray(new CronTrigger[0]));
    return schedulerFactory;
  }

  // LIM: 어떤 클래스에 두면 좋을까... 생각을 해보자
  public static void sendHttpResponseToKafka(JobExecutionContext context, HttpUrl.Builder builder, KafkaProducerConfig kafkaProducerConfig) throws IOException {
    Response response = OkHttpClientConnection.connectOkHttpClient(builder);

    assert response != null;
    if (response.isSuccessful()) {
      String responseBody = response.body().string();

      kafkaProducerConfig.send(context.getJobDetail().getJobDataMap().get("topic").toString(), responseBody);

//      new KafkaConsumerConfig().run();
    }
  }
}