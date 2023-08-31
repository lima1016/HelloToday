package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.JobConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl.Builder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

// 30-cm 구경의 Cassegrain 망원경을 결합하여 달의 통과와 같은 특정 천문학적 사건의 독특한 관점을 포착
@Slf4j
public class EPICImagesCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;


  public EPICImagesCollector() {
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = RegisterBeans.kafkaProducerBean();
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "https://api.nasa.gov/EPIC/api/natural/images";
      Builder builder = connection.buildParameters(url);
      JobConfig.sendHttpResponseToKafka(context, builder, kafkaProducerConfig);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
