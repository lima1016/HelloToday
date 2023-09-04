package com.lima.hellotodayconsole.domain.dashboard.ctl;

import static com.mongodb.client.model.Projections.include;

import com.lima.hellotodayconsole.common.HelloJsonResponse;
import com.lima.hellotodaycore.common.config.db.mongo.MongoExecutor;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

  @GetMapping("/v1/read/neo-feed")
  public HelloJsonResponse selectNeoFeed() {

    MongoExecutor mongoExecutor = new MongoExecutor(RegisterJob.NEO_FEED.getTopic());
    FindIterable<Document> nedFeed = mongoExecutor.aggregate();
    nedFeed.sort(include("_id"));
    nedFeed.projection(include("near_earth_objects"));
    nedFeed.limit(1);

    return HelloJsonResponse.getResponse(nedFeed.cursor().next().get("near_earth_objects"));
  }
}
