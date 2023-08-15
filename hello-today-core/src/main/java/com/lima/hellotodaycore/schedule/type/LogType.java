package com.lima.hellotodaycore.schedule.type;

import lombok.Getter;

@Getter
public enum LogType {
  MID_FCST("getMidFcst", "stnld", "tb_hello_mid", "중기 전망 조회")
  , MID_LAND_FCST("getMidLandFcst", "regld", "tb_hello_mid_land", "중기 육상 예보 조회")
  , MID_SEA_FCST("getMidSeaFcst", "regld", "tb_hello_mid_sea", "중기 기온 조회")
  , MID_TA("getMidTa", "regld", "tb_hello_mid_ta", "중기 해상 예보 조회")
  ;

  private final String logApi;
  private final String code; // 지점 번호
  private final String topic;
  private final String description;

  LogType(String logApi, String code, String topic, String description) {
    this.logApi = logApi;
    this.code = code;
    this.topic = topic;
    this.description = description;
  }
}
