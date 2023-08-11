package com.lima.hellotodaycore.batch.log.midcast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lima.hellotodaycore.common.utils.JsonUtils;
import com.lima.hellotodaycore.connection.http.HttpConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Component
public class CollectLog {

  @Value("${data-api-key}")
  private String apiKey;

  public void collectLog() {
    try {
      StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst"); /*URL*/
      urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(apiKey); /*Service Key*/
      urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); /*페이지번호*/
      urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("10", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
      urlBuilder.append("&").append(URLEncoder.encode("dataType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("JSON", StandardCharsets.UTF_8)); /*요청자료형식(XML/JSON)Default: XML*/
      urlBuilder.append("&").append(URLEncoder.encode("stnId", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("108", StandardCharsets.UTF_8)); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
      urlBuilder.append("&").append(URLEncoder.encode("tmFc", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("201310170600", StandardCharsets.UTF_8)); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/

      HttpURLConnection conn = HttpConnection.getHttpURLConnection(urlBuilder);

      try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
          sb.append(line);
        }

        JsonNode deserialize = JsonUtils.deserialize(sb.toString(), JsonNode.class);
        System.out.println(deserialize.get("response").get("body").get("items"));
      }

      conn.disconnect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

// docker https://tychejin.tistory.com/361

  public static void main(String[] args) {
    try {
      String apiKey = "";
      StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst"); /*URL*/
      urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(apiKey); /*Service Key*/
      urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); /*페이지번호*/
      urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("10", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
      urlBuilder.append("&").append(URLEncoder.encode("dataType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("JSON", StandardCharsets.UTF_8)); /*요청자료형식(XML/JSON)Default: XML*/
      urlBuilder.append("&").append(URLEncoder.encode("stnId", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("108", StandardCharsets.UTF_8)); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
      urlBuilder.append("&").append(URLEncoder.encode("tmFc", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("202308110600", StandardCharsets.UTF_8)); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/

      HttpURLConnection conn = HttpConnection.getHttpURLConnection(urlBuilder);

      try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
          sb.append(line);
        }

        JsonNode deserialize = JsonUtils.deserialize(sb.toString(), JsonNode.class);
        System.out.println(deserialize);
        System.out.println(deserialize.get("response").get("body").get("items").get("item"));
      }

      conn.disconnect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
