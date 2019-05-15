package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTokenTest {

  @Test
  @DisplayName("UserToken.ofで返されるオブジェクトがnullではないこと")
  void test1() {
    UserToken testToken = UserToken.of("testToken");
    assertThat(testToken).isInstanceOf(UserToken.class);
  }

  @Test
  void tokenが7文字で例外が発生する() {
    assertThrows(IllegalArgumentException.class, () -> UserToken.of("aaaaaaa").validate());
  }

  @Test
  void tokenが8文字で例外が発生しない() {
    assertDoesNotThrow(() -> UserToken.of("aaaaaaaa").validate());
  }

  @Test
  void tokenが9文字で例外が発生しない() {
    assertDoesNotThrow(() -> UserToken.of("aaaaaaaaa").validate());
  }

  @Test
  void tokenが126文字で例外が発生しない() {
    assertDoesNotThrow(() -> UserToken.of("a".repeat(126))
            .validate());
  }

  @Test
  void tokenが127文字で例外が発生しない() {
    assertDoesNotThrow(() -> UserToken.of("a".repeat(127))
            .validate());
  }

  @Test
  void tokenが128文字で例外が発生する() {
    assertThrows(IllegalArgumentException.class, () -> UserToken.of("a".repeat(128))
            .validate());
  }

  @Test
  void tokenに記号が入っていると例外が発生する() {
    assertThrows(IllegalArgumentException.class, () -> UserToken.of("?aaaaaaaaaaa").validate());
  }

  @Test
  void valueが正しいかどうか() {
    String value = "testToken";
    UserToken userToken = UserToken.of(value);
    assertThat(value).isEqualTo(userToken.value());
  }
}