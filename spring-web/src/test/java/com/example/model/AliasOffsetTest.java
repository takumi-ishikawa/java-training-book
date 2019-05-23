package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AliasOffsetTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("AliasOffset.ofで返されるオブジェクトがnullではないこと")
    void test1() {
      AliasOffset testAliasOffset = AliasOffset.of(2, 3);
      assertThat(testAliasOffset).isInstanceOf(AliasOffset.class);
    }
  }

  @Nested
  class ValueTest {
    @Test
    void pageが2でsizeが3の場合はvalueが6になると良い() {
      assertThat(AliasOffset.of(2, 3).value()).isEqualTo(6);
    }
  }
}
