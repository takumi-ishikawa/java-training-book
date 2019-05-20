package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AliasTest {

  @Test
  @DisplayName("Alias.ofで返されるオブジェクトがnullではないこと")
  void test1() {
    Alias testAlias =
        Alias.of(
            AliasId.of(1234),
            UserId.of(5678),
            AliasName.of("testAliasName"),
            AliasValue.of("testAliasValue"),
            CreatedAt.of(LocalDateTime.of(2019, 1, 1, 0, 0, 0)));
    assertThat(testAlias).isInstanceOf(Alias.class);
  }
}
