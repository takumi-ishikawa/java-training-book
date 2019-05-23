package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MiddlePageTest {

  @Test
  void sizeとseponseSizeが同じ場合はnullが返らないのが正しい() {
    assertNotNull(AliasNextPage.of(1, 2, 2).valueOrNull());
  }

  @Test
  void sizeとresponseSizeが同じ場合はpageに1足した値をもっているのが正しい() {
    assertThat(AliasNextPage.of(1, 2, 2).valueOrNull()).isEqualTo(2);
  }
}
