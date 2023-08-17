package com.lima.hellotodaycore.schedule;

import com.lima.hellotodaycore.schedule.batch.log.midcast.CollectMidCastLog;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

// 스케쥴러와 작업 세부 정보 설정
@Configuration
public class LogCollectScheduleConfig {

  // Job 상세 정보 설정. 스케줄링 작업을 실행할 클래스 지정
  @Bean
  public JobDetail myJobDetail() {
    JobDetailFactoryBean jobFactory = new JobDetailFactoryBean();
    jobFactory.setJobClass(CollectMidCastLog.class); // 작업을 실행할 클래스
    jobFactory.setName("My-Job"); // 작업의 이름
    jobFactory.setDurability(true); // 작업이 영구적인지 여부
//    jobFactory.setRequestsRecovery(true); // 실패한 작업 재실행 여부
    Map<String, Object> data = new HashMap<>();
    data.put("key", "이건 뭐냐");
    jobFactory.setJobDataAsMap(data);

    jobFactory.afterPropertiesSet();
    return jobFactory.getObject();
  }

  // Cron 표현식을 사용하여 특정 시간에 작업을 실행하도록 설정.
  @Bean
  CronTrigger cronTrigger(JobDetail myJobDetail) {
    CronTriggerFactoryBean triggerFactory;
    try {
      triggerFactory = new CronTriggerFactoryBean();
      triggerFactory.setJobDetail(myJobDetail);
      triggerFactory.setCronExpression("0 0/1 * * * ?"); // 매일 오전 6시마다 0 0/5 * * * ? => 매 5분 마다
      triggerFactory.setDescription("");
      Map<String, Object> data = new HashMap<>();
      data.put("key", "1분간격 시작!");
      triggerFactory.setJobDataAsMap(data);

      triggerFactory.afterPropertiesSet();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return triggerFactory.getObject();
  }

  @Bean
  public SchedulerFactoryBean schedulerFactory(Trigger trigger) {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    // thread pool 설정
//    Properties properties = new Properties();
//    properties.put("threadPool.threadCount", 3);
//    schedulerFactory.setQuartzProperties(properties);

    // 설정한 스레드 풀 설정 정보를 담기
    schedulerFactory.setTriggers(trigger);

    return schedulerFactory;
  }
}