package com.example;

import com.example.service.TimeServiceImpl;
import java.time.Instant;

public class TimerApp {

  private final TimeService timeService;

  public TimerApp(final TimeService timeService) {
    this.timeService = timeService;
  }

  private static TimerApp getInstance() {
    //throw new UnsupportedOperationException("not implemented");
    return new TimerApp(new TimeServiceImpl());
  }

  public static void main(String[] args) {
    final TimerApp instance = getInstance();
    for (int i = 0; i < 10; i++) {
      final Instant currentTime = instance.timeService.getCurrentTime();
      System.out.println(currentTime);
    }
  }
}
