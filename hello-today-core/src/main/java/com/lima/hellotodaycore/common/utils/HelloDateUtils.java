package com.lima.hellotodaycore.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HelloDateUtils {

  public static final String yyyyMMdd = "yyyyMMdd";

  public static String convertLocalDateNow(String dateFormat) {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return currentDate.format(formatter);
  }
}
