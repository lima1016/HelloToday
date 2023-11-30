package com.lima.hellotodaycore.common.config.http;


import com.lima.hellotodaycore.common.utils.DateTimeUtils;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 실제 작업이 수행될 클래스 생성
@Slf4j
@Component
public class OkHttpClientConnection {

  @Value("${api-key}")
  private String apiKey;

  private static class OkHttpClientSingleton {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
  }

  public static OkHttpClient getInstance() {
    return OkHttpClientConnection.OkHttpClientSingleton.OK_HTTP_CLIENT;
  }


  public HttpUrl.Builder buildParameters(String url) {
    log.info("[time: " + DateTimeUtils.getNowStandardTime() + "] - url :" + url);
      HttpUrl.Builder urlBuilder = Objects.requireNonNull(
          Objects.requireNonNull(HttpUrl.parse(url))).newBuilder();
      urlBuilder.addQueryParameter("api_key", apiKey);
      return urlBuilder;
  }

  public HttpUrl.Builder buildUrl(String url) {
    log.info("[time: " + DateTimeUtils.getNowStandardTime() + "] - url :" + url);
    return Objects.requireNonNull(
        Objects.requireNonNull(HttpUrl.parse(url))).newBuilder();
  }

  public static Response connectOkHttpClient (HttpUrl.Builder urlBuilder) {
    try {
      String url = urlBuilder.build().toString();
      Request request = new Request.Builder()
          .url(url)
          .build();

      return OkHttpClientConnection.getInstance().newCall(request).execute();
    } catch (Exception e) {
      log.error("", e);
    }
    return null;
  }

  private void postRequestOkHttp() throws IOException {
    OkHttpClient client = new OkHttpClient();

    RequestBody requestBody = new FormBody.Builder()
        .add("key1", "value1")
        .add("key2", "value2")
        .build();

    Request request = new Request.Builder()
        .url("https://api.example.com/post")
        .post(requestBody)
        .build();

    Response response = client.newCall(request).execute();

    if (response.isSuccessful()) {
      String responseBody = response.body().string();
      System.out.println(responseBody);
    }
  }

  /*
  Async"는 "Asynchronous"의 줄임말로, 컴퓨팅의 컨텍스트에서는 동시에 여러 작업이 실행되는 것을 의미합니다. 특히, 작업이 백그라운드에서 실행되고 결과를 기다리는 동안 다른 작업이 중단되지 않도록 할 때 비동기 방식이 사용됩니다.
  Async 요청의 주요 특징은 다음과 같습니다:
  Non-blocking: Async 작업은 메인 스레드나 호출 스레드를 차단(block)하지 않습니다. 대신, 다른 스레드나 프로세스에서 작업을 수행하고 완료되면 결과를 알려줍니다.
  병렬 처리: 여러 비동기 작업을 동시에 시작할 수 있으며, 각 작업은 독립적으로 실행됩니다.
  콜백: 비동기 작업이 완료되면, 종종 "콜백" 함수나 메서드를 사용하여 결과를 반환하거나 작업의 완료를 알립니다.
  웹 요청, 파일 I/O, 데이터베이스 작업 등과 같은 대기 시간이 발생할 수 있는 작업은 비동기 방식으로 처리하는 것이 효율적입니다. 비동기 방식으로 처리하면, 작업이 진행되는 동안 프로그램이 멈추거나 다른 작업을 기다리는 시간을 줄일 수 있습니다.
   */
  private void asyncRequestOkHttp() {
    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
        .url("https://api.example.com/data")
        .build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
          String responseBody = response.body().string();
          System.out.println(responseBody);
        }
      }
    });

  }
}