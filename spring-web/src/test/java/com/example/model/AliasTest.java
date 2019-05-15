package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AliasTest {

  @Test
  @DisplayName("Alias.ofで返されるオブジェクトがnullではないこと")
  void test1() {
    Alias testAlias = Alias.of(AliasId.of(1234),
            UserId.of(5678),
            new AliasName("testAliasName"),
            AliasValue.of("testAliasValue"),
            CreatedAt.of(LocalDateTime.of(2019, 1, 1, 0, 0, 0)));
    assertThat(testAlias).isInstanceOf(Alias.class);
  }
}