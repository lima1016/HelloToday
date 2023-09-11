package com.lima.hellotodayconsole.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class HelloJsonResponse {
  @JsonProperty("hello_code")
  private String code;

  @JsonProperty("hello_msg")
  private String msg;

  @JsonProperty("response")
  private Object response;

  public static HelloJsonResponse getResponse(Object response) {
    return HelloJsonResponse.builder()
        .code("Hello-00000")
        .msg("SUCCESS")
        .response(response)
        .build();
  }

}
