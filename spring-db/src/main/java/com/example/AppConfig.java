package com.example;

import com.example.repository.IdRepository;
import com.example.repository.impl.IdRepositoryImpl;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoField;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  RandomNameGenerator randomNameGenerator(Clock clock) {
    final Instant now = Instant.now(clock);
    return new RandomNameGenerator(now.get(ChronoField.MILLI_OF_SECOND));
  }

  @Bean
  IdRepository idRepository(@Value("${app.num}") int num, Clock clock) {
    return new IdRepositoryImpl((short) num, clock);
  }

}
