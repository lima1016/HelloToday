package com.lima.hellotodaycore.common.db.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MongoConnection {

  private final String host = "localhost";
  private final Integer port = 27017;

  private static class MongoDBSingleton {
    private static final MongoDatabase MONGO_SINGLETON = new MongoConnection().connect();
  }

  public static MongoDatabase getInstance() {
    return MongoDBSingleton.MONGO_SINGLETON;
  }

  public MongoDatabase connect() {
    String databaseName = "hellonosql";
    String authenticationDB = "admin";

    log.info("databaseName : {}, host : {}, port : {}", databaseName, host, port);
    MongoCredential credential = MongoCredential.createCredential("hellomongo", authenticationDB, "1016".toCharArray());
    try {
      MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
          .applyToClusterSettings(builder -> builder.hosts(List.of(new ServerAddress(host, port))))
          .credential(credential)
          .build());

      return mongoClient.getDatabase(databaseName);
    } catch (Exception e) {
      log.error("MongoDB connection failed", e);
      return null;
    }
  }

}
