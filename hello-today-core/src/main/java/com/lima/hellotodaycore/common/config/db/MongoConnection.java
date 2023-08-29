package com.lima.hellotodaycore.common.config.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MongoConnection {
  @Value("${spring.data.mongodb.authentication-database}")
  private String databaseName;

  @Value("${spring.data.mongodb.host}")
  private String host;

  @Value("${spring.data.mongodb.port}")
  private Integer port;

  private static class MongoDBSingleton {
    private static final MongoConnection MONGO_SINGLETON = new MongoConnection();
  }

  public static MongoConnection getInstance() {
    return MongoDBSingleton.MONGO_SINGLETON;
  }

  @Bean
  public MongoDatabase connect() {
    log.info("databaseName : " + databaseName + ", host : " + host + ", port : " + port);
    MongoCredential credential = MongoCredential.createCredential("hellomongo", databaseName, "1016".toCharArray());
    try {
      MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
          .applyToClusterSettings(builder -> builder.hosts(List.of(new ServerAddress("localhost", 27017))))
          .credential(credential)
          .build());

      return mongoClient.getDatabase(databaseName);
    } catch (Exception e) {
      log.error("", e);
      return null;
    }
  }

  // 이부분은 따로 분리 시키자 나중에... 여기서는 connection만 할 예정
  public void insertOne(String collectionName, Map<String, Object> data) {
    log.info("collectionName : " + collectionName + ", data : " + data);
    // collection 선택
    MongoCollection<Document> collection = MongoConnection.getInstance().connect().getCollection(collectionName);
    // 저장할 데이터 생성
    Document doc = new Document(data);
    collection.insertOne(doc);
  }
}
