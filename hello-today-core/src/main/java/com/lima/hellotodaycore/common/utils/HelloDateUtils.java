package com.lima.hellotodaycore.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HelloDateUtils {

  public static final String LOG_DATE_FORMAT = "yyyy-MM-dd";
  public static final String STANDARD_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

  public static String convertLocalDateNow(String dateFormat) {
    LocalDate now = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return now.format(formatter);
  }

  public static String calculateFromDate(String startDate, int days) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(LOG_DATE_FORMAT);
    Calendar cal = Calendar.getInstance();;
    try {
      Date parsedDate = dateFormat.parse(startDate);
      cal.setTime(parsedDate);
      cal.add(Calendar.DATE, -days);

      return dateFormat.format(cal.getTime());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static String getNowStandardTime() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT);

    return now.format(formatter);
  }
}
