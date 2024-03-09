package com.lima.hellotodaycore.common.db.opensearch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

public class OpenSearchSearching {

    public void connectOpenSearch() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("호스트_주소", 9200, "http"));

        RestClient restClient = builder.build();

        try {
            // 요청 본문을 JSON 형식으로 작성
            String query = "{\"query\":{\"match_all\":{}}}";
            HttpEntity entity = new StringEntity(query, ContentType.APPLICATION_JSON);

            // 검색 요청 생성
            Request request = new Request("GET", "/인덱스_이름/_search");
            request.setEntity(entity);

            // 요청 수행 및 응답 받기
            org.elasticsearch.client.Response response = restClient.performRequest(request);

            // 응답 읽기 및 처리
            System.out.println(org.apache.http.util.EntityUtils.toString(response.getEntity()));

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
