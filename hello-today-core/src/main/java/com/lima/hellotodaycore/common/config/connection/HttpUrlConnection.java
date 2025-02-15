package com.lima.hellotodaycore.common.config.connection;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpUrlConnection {
    private final static String APPLICATION_JSON = "application/json";
    private final static String CONTENT_TYPE = "Content-type";
    private final static String GET_METHOD = "GET";

    public static HttpURLConnection getHttpURLConnection(String connectionUrl, String method) {
        try {
            URL url = new URL(connectionUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            return conn;
        } catch (IOException e) {
            log.error("", e);
        }
      return null;
    }
}
