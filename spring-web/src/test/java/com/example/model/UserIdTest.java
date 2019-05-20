package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserIdTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("UserId.ofで返されるオブジェクトがnullではないこと")
    void test1() {
      UserId testId = UserId.of(1234);
      assertThat(testId).isInstanceOf(UserId.class);
    }
  }
}
