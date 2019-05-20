package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CreatedAtTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("CreatedAt.of(Instant)で返されるオブジェクトがnullではないこと")
    void test1() {
      CreatedAt testCreatedAt =
          CreatedAt.of(LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC));
      assertThat(testCreatedAt).isInstanceOf(CreatedAt.class);
    }

    @Test
    @DisplayName("CreatedAt.of(LocalDateTime)で返されるオブジェクトがnullではないこと")
    void test2() {
      CreatedAt testCreatedAt = CreatedAt.of(LocalDateTime.of(2019, 1, 1, 0, 0, 0));
      assertThat(testCreatedAt).isInstanceOf(CreatedAt.class);
    }
  }
}
