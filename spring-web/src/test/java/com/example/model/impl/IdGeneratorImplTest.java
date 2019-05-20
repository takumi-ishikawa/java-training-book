package com.example.model.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.model.IdGenerator;
import java.time.Clock;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IdGeneratorImplTest {
  final IdGenerator idGenerator = new IdGeneratorImpl((short) 3, Clock.systemUTC());

  @Nested
  class createIdTest {
    @Test
    void 返り血がLongであると良い() {
      assertThat(idGenerator.createId()).isInstanceOf(Long.class);
    }

    @Test
    void ほぼ同時にcreateIdしたときに異なる値が返ってくれば良い() {
      long id1 = idGenerator.createId();
      //      try {
      //        Thread.sleep(0);
      //      } catch (InterruptedException e) {
      //        e.printStackTrace();
      //      }
      long id2 = idGenerator.createId();
      assertThat(id1).isNotEqualTo(id2);
    }
  }
}
