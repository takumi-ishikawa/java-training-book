package com.example.service;

import com.example.TimeService;
import java.time.Clock;
import java.time.Instant;

public class TimeServiceImpl implements TimeService {

  @Override
  public Instant getCurrentTime() {
    return Instant.now(Clock.systemUTC());
  }
}
