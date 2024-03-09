package com.lima.hellotodaycore.common.db.opensearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

public class OpenSearchCreating {

    private String index;


    public OpenSearchCreating(String index) {
        this.index = index;
    }

    public void createData(String log) {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("호스트_주소", 9200, "http"));

        RestClient restClient = builder.build();

        try {
            // PUT 요청 생성
            Request request = new Request("PUT", "/" + index +"/_doc/문서_ID");
            // 색인할 데이터(JSON 형식) 생성
            request.setJsonEntity(log);

            // 요청 수행 및 응답 받기
            org.elasticsearch.client.Response response = restClient.performRequest(request);

            // 응답 읽기
            System.out.println("응답 코드: " + response.getStatusLine().getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
