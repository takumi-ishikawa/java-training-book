package com.example.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FinalPageTest {

  @Test
  void sizeとresoinsePageが異なる場合はnullをもっているのが正しい() {
    assertNull(AliasNextPage.of(1, 2, 3).valueOrNull());
  }
}
