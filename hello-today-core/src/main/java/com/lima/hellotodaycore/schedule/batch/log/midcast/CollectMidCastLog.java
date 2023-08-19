package com.lima.hellotodaycore.schedule.batch.log.midcast;


import com.fasterxml.jackson.databind.JsonNode;
import com.lima.hellotodaycore.common.config.http.HttpConnection;
import com.lima.hellotodaycore.common.utils.BeansUtils;
import com.lima.hellotodaycore.common.utils.HelloDateUtils;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import com.lima.hellotodaycore.domain.midcast.entity.MidCastLog;
import com.lima.hellotodaycore.domain.midcast.svc.LocationCodeService;
import com.lima.hellotodaycore.kafka.consumer.KafkaConsumerConfig;
import com.lima.hellotodaycore.kafka.producer.KafkaProducerConfig;
import com.lima.hellotodaycore.schedule.type.LogType;
import com.lima.hellotodaycore.schedule.type.ResponseType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

// 실제 작업이 수행될 클래스 생성
@Slf4j
public class CollectMidCastLog implements Job {

  private LocationCodeService codeService;
  private KafkaProducerConfig kafkaProducerConfig;

  public CollectMidCastLog() {
    this.kafkaProducerConfig = BeansUtils.getBean(KafkaProducerConfig.class);
    this.codeService = BeansUtils.getBean(LocationCodeService.class);
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      log.info("============================ collect mid cast log ============================");
      for (LogType value : LogType.values()) {
        // 구역 코드 구하기
        MidCastLog locationCode = codeService.getLocationCode(value);
        String logApi = "http://apis.data.go.kr/1360000/MidFcstInfoService/" + value.getLogApi();
        String apiKey = "3g%2Fd4w%2FtARZLfMnVnR4Qp%2FBSmp5Y6ruf5IH9X1FIgMomKQQYCbI5pn1NBlKWJDQgfkswjUJfBwVfp5tT%2FGTU4g%3D%3D";

        StringBuilder urlBuilder = new StringBuilder(logApi);
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(apiKey);
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("10", StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("dataType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("JSON", StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode(value.getCode(), StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(locationCode.getLocationCode(), StandardCharsets.UTF_8)); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("tmFc", StandardCharsets.UTF_8)).append("=")
            .append(URLEncoder.encode(HelloDateUtils.convertLocalDateNow(HelloDateUtils.yyyyMMdd) + "0600", StandardCharsets.UTF_8)); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/

        HttpURLConnection conn = HttpConnection.getHttpURLConnection(urlBuilder);

        assert conn != null;
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
          StringBuilder sb = new StringBuilder();
          String line;
          while ((line = rd.readLine()) != null) {
            sb.append(line);
          }

          System.out.println("sb.toString() >>> " + sb);
          JsonNode deserialize = JsonUtils.deserialize(sb.toString(), JsonNode.class);

          assert deserialize != null;
          kafkaProducerConfig.send(value.getTopic()
              , deserialize.get(ResponseType.RESPONSE.getType())
              .get(ResponseType.BODY.getType())
              .get(ResponseType.ITEMS.getType())
              .get(ResponseType.ITEM.getType()).toString());
        }
        conn.disconnect();
      }

      new KafkaConsumerConfig().run();

    } catch (Exception e) {
      log.error("", e);
    }
  }
}