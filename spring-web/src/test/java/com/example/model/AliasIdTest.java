package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AliasIdTest {

  @Test
  @DisplayName("AliasId.ofで返されるオブジェクトがnullではないこと")
  void test1() {
    AliasId testAliasId = AliasId.of(1234);
    assertThat(testAliasId).isInstanceOf(AliasId.class);
  }
}