package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void pageが0でsizeが0の場合は例外が発生しない() {
      AliasOffset.of(0, 0);
    }

    @Test
    void pageが0でsizeがマイナス1の場合は例外が発生する() {
      assertThatThrownBy(() -> AliasOffset.of(0, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void pageがマイナス1でsizeが0の場合は例外が発生する() {
      assertThatThrownBy(() -> AliasOffset.of(-1, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void pageがマイナス1でsizeがマイナス1の場合は例外が発生する() {
      assertThatThrownBy(() -> AliasOffset.of(-1, -1)).isInstanceOf(IllegalArgumentException.class);
    }
  }
}
