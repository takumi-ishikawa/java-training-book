package com.example.repository.impl;

import com.example.repository.IdRepository;
import java.nio.ByteBuffer;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

public class IdRepositoryImpl implements IdRepository {

  private static final Instant BASE = LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);

  private final short num;
  private final Clock clock;

  public IdRepositoryImpl(final short num, final Clock clock) {
    this.num = num;
    this.clock = clock;
  }

  @Override
  public long createId() {
    final Instant now = Instant.now(clock);
    final Duration duration = Duration.between(BASE, now);
    final int seconds = (int) duration.getSeconds();
    final short milli = (short) now.get(ChronoField.MILLI_OF_SECOND);
    final ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.putShort(num);
    buffer.putInt(seconds);
    buffer.putShort(milli);
    buffer.flip();
    return buffer.getLong();
  }
}
