package com.example;

import com.example.model.IdGenerator;
import com.example.model.impl.IdGeneratorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoField;

@Configuration
public class AppConfig {

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  IdGenerator idGenerator(@Value("${app.num}") int num, Clock clock) {
    return new IdGeneratorImpl((short) num, clock);
  }

}
