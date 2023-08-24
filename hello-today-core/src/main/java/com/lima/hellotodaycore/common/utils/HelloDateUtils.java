package com.lima.hellotodaycore.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloDateUtils {

  public static final String yyyyMMdd = "yyyyMMdd";
  public static final String STANDARD_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

  public static String convertLocalDateNow(String dateFormat) {
    LocalDate now = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return now.format(formatter);
  }

  public static String getNowStandardTime() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT);
    return now.format(formatter);
  }
}
