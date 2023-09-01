package com.lima.hellotodayconsole.domain.dashboard.ctl;

import static com.mongodb.client.model.Projections.include;

import com.lima.hellotodaycore.common.config.db.mongo.MongoExecutor;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  @GetMapping("/read/ne0-feed")
  public void selectNeoFeed() {

    // LIM: core에 mongo가 있어서 database가 null로 나옴 해결해야함
    MongoExecutor mongoExecutor = new MongoExecutor();
    FindIterable<Document> nedFeed = mongoExecutor.aggregate("tb_hello_ned_feed");
    nedFeed.projection(include("near_earth_objects"));

    for (Document document : nedFeed) {
      System.out.println(document.toJson());
    }
  }
}
