package com.example.model.impl;

import com.example.model.IdGenerator;
import java.nio.ByteBuffer;
import java.time.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IdGeneratorImpl implements IdGenerator {

  private static final Instant BASE =
      LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);

  private final AtomicInteger count;
  private final short num;
  private final Clock clock;;

  public IdGeneratorImpl(@Value("${app.num}") int num, Clock clock) {
    this.num = (short) num;
    this.clock = clock;
    this.count = new AtomicInteger(0);
  }

  @Scheduled(fixedRate = 1000L)
  public void clearCount() {
    count.set(0);
  }

  @Override
  public long createId() {
    final Instant now = Instant.now(clock);
    final Duration duration = Duration.between(BASE, now);
    final int seconds = (int) duration.getSeconds();
    final ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.putShort(num);
    buffer.putInt(seconds);
    buffer.putShort((short) count.incrementAndGet());
    buffer.flip();
    return buffer.getLong();
  }
}
