package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AliasValueTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("AliasValue.ofで返されるオブジェクトがnullではないこと")
    void test1() {
      AliasValue testAliasValue = AliasValue.of("foobar");
      assertThat(testAliasValue).isInstanceOf(AliasValue.class);
    }
  }
}