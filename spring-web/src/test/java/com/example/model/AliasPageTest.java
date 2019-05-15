package com.example.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AliasPageTest {

  @Test
  void valueがマイナス1のとき例外が発生する() {
    assertThrows(IllegalArgumentException.class, () -> AliasPage.of(-1).validate());
  }

  @Test
  void valueが0のとき例外が発生しない() {
    assertDoesNotThrow(() -> AliasPage.of(0).validate());
  }

  @Test
  void offsetの計算が正しいかどうか() {
    assertThat(AliasPage.of(2).toOffset(AliasSize.of(3)).value()).isEqualTo(2 * 3);
  }
}