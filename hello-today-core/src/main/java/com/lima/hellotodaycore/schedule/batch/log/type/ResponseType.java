package com.lima.hellotodaycore.schedule.batch.log.type;

import lombok.Getter;

@Getter
public enum ResponseType {
  RESPONSE("response")
  , BODY("body")
  , ITEMS("items")
  , ITEM("item")
  ;

  private String type;

  ResponseType(String type) {
    this.type = type;
  }
}