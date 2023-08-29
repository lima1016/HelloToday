package com.lima.hellotodayconsole.domain.dashboard.ctl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

  @GetMapping("/")
  public void main() {

  }
}
