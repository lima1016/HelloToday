package com.lima.hellotodaycore.common.config.db.mongo;

import com.lima.hellotodaycore.common.config.RegisterBeans;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
public class MongoExecutor {

  private final MongoConnection mongoConnection;

  public MongoExecutor() {
    this.mongoConnection = RegisterBeans.mongoConnectionBean();
  }

  // 이부분은 따로 분리 시키자 나중에... 여기서는 connection만 할 예정
  public void insertOne(String collectionName, Map<String, Object> data) {
    log.info("collectionName : " + collectionName + ", data : " + data);
    // collection 선택
    MongoCollection<Document> collection = mongoConnection.connect().getCollection(collectionName);
    // 저장할 데이터 생성
    Document doc = new Document(data);
    collection.insertOne(doc);
  }

  // LIM: 연구중....
  public <T> void insert(String collectionName, T data) {
    log.info("collectionName : " + collectionName + ", data : " + data);
    List<Document> docs = new ArrayList<>();
    // collection 선택
    MongoCollection<Document> collection = mongoConnection.connect().getCollection(collectionName);
    // didn't check insert Many
    Document doc = new Document((Map<String, ?>) data);
    docs.add(doc);
    collection.insertMany(docs);
  }

  public FindIterable<Document> aggregate(String collectionName) {
    MongoCollection<Document> collection = mongoConnection.connect().getCollection(collectionName);
    return collection.find();
  }
}
