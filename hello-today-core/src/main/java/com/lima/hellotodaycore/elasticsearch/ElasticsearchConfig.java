package com.lima.hellotodaycore.elasticsearch;

import com.lima.hellotodaycore.common.config.http.HttpUrlConnection;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticsearchConfig {

  public void init() {
    // Test 중
    String url = "http://localhost:9200/users/_doc/1";
    try {
      HttpURLConnection conn = HttpUrlConnection.getHttpURLConnection(url, "PUT");

      String input = "json 형식 넣고";

      assert conn != null;
      OutputStream os = conn.getOutputStream();

      os.write(input.getBytes());
      os.flush();

      conn.disconnect();
    } catch (Exception e) {
      log.error("", e);
    }

  }
}
