package com.example.model;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinalPageTest {

  @Test
  void sizeとresoinsePageが異なる場合はnullが返ってくると正しい() {
    assertNull(AliasNextPage.of(1, 2, 3).valueOrNull());
  }
}