package com.example.service;

import com.example.TimeService;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Instant;

public class TimeServiceImpl implements TimeService {

  @Inject
  @Override
  public Instant getCurrentTime() {
    return Instant.now(Clock.systemUTC());
  }
}
