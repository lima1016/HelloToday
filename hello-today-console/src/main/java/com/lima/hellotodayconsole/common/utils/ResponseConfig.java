package com.lima.hellotodayconsole.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseConfig {

  @JsonProperty("code")
  private String code = "Hello-00000";

  @JsonProperty("msg")
  private String msg = "SUCCESS";

  // 응답쪽 수정해야함
  public static ResponseConfig isEmpty() {
    return new ResponseConfig();
  }
}
