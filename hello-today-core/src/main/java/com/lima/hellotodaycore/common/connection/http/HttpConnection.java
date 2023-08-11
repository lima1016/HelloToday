package com.lima.hellotodaycore.common.connection.http;

import java.net.HttpRetryException;
import java.net.http.HttpConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpConnection {
    private final static String APPLICATION_JSON = "application/json";
    private final static String CONTENT_TYPE = "Content-type";
    private final static String GET_METHOD = "GET";

    public static HttpURLConnection getHttpURLConnection(StringBuilder stringBuilder) {
        try {
            URL url = new URL(stringBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(GET_METHOD);
            conn.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            return conn;
        } catch (IOException e) {
            log.error("", e);
        }
      return null;
    }
}
