package com.lima.hellotodayconsole.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseConfig {

  @JsonProperty("battle_code")
  private String code = "BATTLE-00000";

  @JsonProperty("battle_msg")
  private String msg = "SUCCESS";

  // 응답쪽 수정해야함
  public static ResponseConfig isEmpty() {
    return new ResponseConfig();
  }
}
