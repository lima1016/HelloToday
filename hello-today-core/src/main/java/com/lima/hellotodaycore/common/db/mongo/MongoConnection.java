package com.lima.hellotodaycore.common.db.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MongoConnection {
  // console 에서 부를때 value 값이 안불러짐
  @Value("${spring.data.mongodb.authentication-database}")
  private String databaseName = "hellonosql";

  @Value("${spring.data.mongodb.host}")
  private String host = "localhost";

  @Value("${spring.data.mongodb.port}")
  private Integer port = 27017;

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
          .applyToClusterSettings(builder -> builder.hosts(List.of(new ServerAddress(host, port))))
          .credential(credential)
          .build());

      return mongoClient.getDatabase(databaseName);
    } catch (Exception e) {
      log.error("", e);
      return null;
    }
  }

}
