package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AliasNameTest {
  @Test
  @DisplayName("AliasName.ofで返されるオブジェクトがnullではないこと")
  void test1() {
    AliasName testAliasName = AliasName.of("testAliasName");
    assertThat(testAliasName).isInstanceOf(AliasName.class);
  }
}
