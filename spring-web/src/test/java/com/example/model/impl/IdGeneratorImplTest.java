package com.example.model.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.model.IdGenerator;
import java.time.Clock;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IdGeneratorImplTest {
  final int NUM_OF_TIMES_TO_CREATE_ID = 100;
  final IdGenerator idGenerator = new IdGeneratorImpl((short) 3, Clock.systemUTC());

  @Nested
  class createIdTest {
    @Test
    void 返り値がLongであると良い() {
      assertThat(idGenerator.createId()).isInstanceOf(Long.class);
    }

    @Test
    void ほぼ同時にcreateIdしたときに異なる値が返ってくれば良い() {
      for (int i = 0; i < NUM_OF_TIMES_TO_CREATE_ID; i++) {
        long id1 = idGenerator.createId();
        long id2 = idGenerator.createId();
        assertThat(id1).isLessThan(id2);
      }
    }
  }
}
