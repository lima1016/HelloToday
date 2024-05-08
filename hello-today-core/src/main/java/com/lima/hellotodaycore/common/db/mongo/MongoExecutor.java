package com.lima.hellotodaycore.common.db.mongo;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
public class MongoExecutor {

  private final MongoDatabase mongoConnection;

  private String collectionName;

  public MongoExecutor(String collectionName) {
    this.mongoConnection = RegisterBeans.mongoConnectionBean();
    this.collectionName = collectionName;
  }

  public void insertOne(Map<String, Object> data) {
    log.debug("collectionName : " + collectionName + ", data : " + data);
    // collection 선택
    MongoCollection<Document> collection = mongoConnection.getCollection(collectionName);
    // 저장할 데이터 생성
    Document doc = new Document(data);
    collection.insertOne(doc);
  }

  // LIM: 연구중....
  public <T> void insert(String collectionName, T data) {
    log.debug("collectionName : " + collectionName + ", data : " + data);
    List<Document> docs = new ArrayList<>();
    // collection 선택
    MongoCollection<Document> collection = mongoConnection.getCollection(collectionName);
    // didn't check insert Many
    Document doc = new Document((Map<String, ?>) data);
    docs.add(doc);
    collection.insertMany(docs);
  }

  public FindIterable<Document> aggregate() {
    MongoCollection<Document> collection = mongoConnection.getCollection(collectionName);
    return collection.find();
  }
}
