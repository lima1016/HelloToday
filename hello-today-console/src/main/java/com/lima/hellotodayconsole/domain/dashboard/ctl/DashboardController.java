package com.lima.hellotodayconsole.domain.dashboard.ctl;

import static com.mongodb.client.model.Projections.include;

import com.lima.hellotodayconsole.common.utils.HelloResponseUtil;
import com.lima.hellotodaycore.common.config.db.mongo.MongoExecutor;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  @GetMapping("/read/neo-feed")
  public HelloResponseUtil selectNeoFeed() {
    MongoExecutor mongoExecutor = new MongoExecutor(RegisterJob.APOD.getTopic());
    FindIterable<Document> nedFeed = mongoExecutor.aggregate();
    nedFeed.projection(include("near_earth_objects"));
    nedFeed.sort(include("_id"));
    nedFeed.limit(1);

    MongoCursor<Document> cursor = nedFeed.cursor();

    return HelloResponseUtil.getResponse(cursor.next().get("near_earth_objects"));
  }
}
