package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("User.ofで返されるオブジェクトがnullではないこと")
    void test1() {
      User testUser = User.of(UserId.of(1234), UserName.of("test"), UserToken.of("abc1234567890"), CreatedAt.of(LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)));
      assertThat(testUser).isInstanceOf(User.class);
    }
  }
}