package com.lima.hellotodayconsole.domain.dashboard.svc;

import com.lima.hellotodaycore.common.db.mongo.MongoExecutor;
import com.lima.hellotodaycore.schedule.batch.log.RegisterJob;
import com.mongodb.annotations.Sealed;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Projections.include;

@Service
public class DashboardService {

  public FindIterable<Document> getDocuments() {
    MongoExecutor mongoExecutor = new MongoExecutor(RegisterJob.NEO_FEED.getTopic());
    FindIterable<Document> nedFeed = mongoExecutor.aggregate();
    nedFeed.sort(include("_id"));
    nedFeed.projection(include("near_earth_objects"));
    nedFeed.limit(1);
    return nedFeed;
  }
}
