package com.lima.hellotodaycore.schedule.batch.log.collector;

import com.lima.hellotodaycore.common.config.http.OkHttpClientConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.kafka.consumer.KafkaConsumerConfig;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl.Builder;
import okhttp3.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// APOD는 매일 천문학적 관측, 현상, 사진 등에 대한 설명과 함께 이미지나 동영상을 게시
@Slf4j
public class ApodLogCollector implements Job {

  private final OkHttpClientConnection connection;
  private final KafkaProducerConfig kafkaProducerConfig;

  public ApodLogCollector() {
    this.connection = BeansUtils.getBean(OkHttpClientConnection.class);
    this.kafkaProducerConfig = BeansUtils.getBean(KafkaProducerConfig.class);
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      String url = "https://api.nasa.gov/planetary/apod";
      Builder builder = connection.buildParameters(url);
      Response response = OkHttpClientConnection.connectOkHttpClient(builder);

      assert response != null;
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
// {"copyright":"Under dark","date":"2023-08-24","explanation":"Under dark and mostly moonless night skies, many denizens of planet Earth were able to watch this year's Perseid meteor shower. Seen from a grassy hillside from Shiraz, Iran these Perseid meteors streak along the northern summer Milky Way before dawn on Sunday, August 13. Frames used to construct the composited image were captured near the active annual meteor shower's peak between 02:00 AM and 04:30 AM local time. Not in this night skyscape, the shower's radiant in the heroic constellation Perseus is far above the camera's field of view. But fans of northern summer nights can still spot a familiar asterism. Formed by bright stars Deneb, Vega, and Altair, the Summer Triangle spans the luminous band of the Milky Way.","hdurl":"https://apod.nasa.gov/apod/image/2308/MSH21080.jpg","media_type":"image","service_version":"v1","title":"Meteors along the Milky Way","url":"https://apod.nasa.gov/apod/image/2308/MSH11080.jpg"}
        kafkaProducerConfig.send(context.getJobDetail().getJobDataMap().get("topic").toString(), responseBody);

        new KafkaConsumerConfig().run();
      }
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
