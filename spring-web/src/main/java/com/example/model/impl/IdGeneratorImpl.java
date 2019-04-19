package com.example.model.impl;

import com.example.model.IdGenerator;

import java.nio.ByteBuffer;
import java.time.*;
import java.time.temporal.ChronoField;

public class IdGeneratorImpl implements IdGenerator {

    private static final Instant BASE = LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);

    private final short num;
    private final Clock clock;

    public IdGeneratorImpl(final short num, final Clock clock) {

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
