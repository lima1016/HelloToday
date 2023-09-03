package com.lima.hellotodayconsole.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class HelloResponseUtil {

  @JsonProperty("code")
  private String code;

  @JsonProperty("msg")
  private String msg;

  @JsonProperty("response")
  private Object response;

  public static <T> HelloResponseUtil getResponse(T response) {
    return HelloResponseUtil.builder()
        .code("Hello-00000")
        .msg("SUCCESS")
        .response(response)
        .build();
  }
}
