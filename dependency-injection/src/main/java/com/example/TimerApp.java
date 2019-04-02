package com.example;

import com.example.service.TimeServiceImpl;
import java.time.Instant;

public class TimerApp {

  private final TimeServiceImpl timeService;

  public TimerApp(final TimeServiceImpl timeService) {
    this.timeService = timeService;
  }

  private static TimerApp getInstance() {
    throw new UnsupportedOperationException("not implemented");
  }

  public static void main(String[] args) {
    final TimerApp instance = getInstance();
    for (int i = 0; i < 10; i++) {
      final Instant currentTime = instance.timeService.getCurrentTime();
      System.out.println(currentTime);
    }
  }
}
