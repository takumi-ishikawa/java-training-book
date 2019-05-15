package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("UserName.ofで返されるオブジェクトがnullではないこと")
    void test1() {
      UserName testName = UserName.of("testName");
      assertThat(testName).isInstanceOf(UserName.class);
    }
  }

  @Nested
  class ValidateTest {
    @Test
    void tokenが1文字で例外が発生する() {
      assertThrows(IllegalArgumentException.class, () -> UserName.of("a").validate());
    }

    @Test
    void tokenが2文字で例外が発生しない() {
      assertDoesNotThrow(() -> UserName.of("aa").validate());
    }

    @Test
    void tokenが35文字で例外が発生しない() {
      assertDoesNotThrow(() -> UserName.of("a".repeat(35)).validate());
    }

    @Test
    void tokenが36文字で例外が発生する() {
      assertThrows(IllegalArgumentException.class, () -> UserName.of("a".repeat(36))
              .validate());
    }

    @Test
    void tokenに記号が入っていると例外が発生する() {
      assertThrows(IllegalArgumentException.class, () -> UserName.of("?aaaaaaaaaaa").validate());
    }
  }
}