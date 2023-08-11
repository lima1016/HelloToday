package com.lima.hellotodaycore.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

  public static <T> T deserialize(String str, Class<T> clazz) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(str, clazz);
    } catch (Exception e) {
      log.error("", e);
      return null;
    }
  }
}
