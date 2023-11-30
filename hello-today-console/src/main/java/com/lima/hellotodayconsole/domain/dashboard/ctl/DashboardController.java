package com.lima.hellotodayconsole.domain.dashboard.ctl;

import com.lima.hellotodayconsole.common.response.HelloJsonResponse;
import com.lima.hellotodayconsole.domain.dashboard.svc.DashboardService;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  private DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping("/v1/read/neo-feed")
  public HelloJsonResponse selectNeoFeed() {

    return HelloJsonResponse.getResponse(dashboardService.getDocuments());
  }
}
