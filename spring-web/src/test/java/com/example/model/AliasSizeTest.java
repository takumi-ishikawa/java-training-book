package com.example.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AliasSizeTest {

  @Test
  void インクリメントが正しいかどうか() {
    assertThat(AliasSize.of(1).increment()).isEqualTo(AliasSize.of(2));
  }

  @Test
  void デクリメントが正しいかどうか() {
    assertThat(AliasSize.of(1).decrement()).isEqualTo(AliasSize.of(0));
  }

  @Test
  void valueがマイナス1のとき例外が発生する() {
    assertThrows(IllegalArgumentException.class, () -> AliasSize.of(-1).validate());
  }

  @Test
  void valueが0のとき例外が発生しない() {
    assertDoesNotThrow(() -> AliasSize.of(0).validate());
  }
}