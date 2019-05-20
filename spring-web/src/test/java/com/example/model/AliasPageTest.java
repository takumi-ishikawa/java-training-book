package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AliasPageTest {

  @Nested
  class ValidateTest {
    @Test
    void valueがマイナス1のとき例外が発生する() {
      assertThrows(IllegalArgumentException.class, () -> AliasPage.of(-1).validate());
    }

    @Test
    void valueが0のとき例外が発生しない() {
      assertDoesNotThrow(() -> AliasPage.of(0).validate());
    }
  }

  @Nested
  class ToOffsetTest {
    @Test
    void valueに2でsizeが3の場合にoffsetの値が6になる() {
      assertThat(AliasPage.of(2).toOffset(AliasSize.of(3)).value()).isEqualTo(6);
    }
  }
}
