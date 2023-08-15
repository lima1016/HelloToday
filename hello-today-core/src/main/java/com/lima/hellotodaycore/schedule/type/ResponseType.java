package com.lima.hellotodaycore.schedule.type;

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