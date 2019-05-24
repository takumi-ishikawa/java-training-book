package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AliasesTest {

  @Nested
  class OfTest {
    @Test
    @DisplayName("Aliases.ofで返されるオブジェクトがnullではないこと")
    void test1() {
      Aliases testAliases =
          Aliases.of(
              AliasPage.of(1),
              AliasSize.of(2),
              List.of(
                  Alias.of(
                      AliasId.of(1234),
                      UserId.of(5678),
                      AliasName.of("testAliasName"),
                      AliasValue.of("testAliasValue"),
                      CreatedAt.of(
                          LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)))));
      assertThat(testAliases).isInstanceOf(Aliases.class);
    }
  }

  @Nested
  class NextPageTest {
    @Test
    void pageが1でsizeが1である場合にnextPageで返されるオブジェクトの値が2であること() {
      Aliases testAliases =
          Aliases.of(
              AliasPage.of(1),
              AliasSize.of(1),
              List.of(
                  Alias.of(
                      AliasId.of(1234),
                      UserId.of(5678),
                      AliasName.of("testAliasName1"),
                      AliasValue.of("testAliasValue1"),
                      CreatedAt.of(Instant.now())),
                  Alias.of(
                      AliasId.of(1111),
                      UserId.of(2222),
                      AliasName.of("testAliasName2"),
                      AliasValue.of("testAliasValue2"),
                      CreatedAt.of(Instant.now()))));
      assertThat(testAliases.nextPage().valueOrNull()).isEqualTo(2);
    }

    @Test
    void pageが1でsizeが2である場合にnextPageで返されるオブジェクトの値がnullであること() {
      Aliases testAliases =
          Aliases.of(
              AliasPage.of(1),
              AliasSize.of(2),
              List.of(
                  Alias.of(
                      AliasId.of(1234),
                      UserId.of(5678),
                      AliasName.of("testAliasName1"),
                      AliasValue.of("testAliasValue1"),
                      CreatedAt.of(Instant.now())),
                  Alias.of(
                      AliasId.of(1111),
                      UserId.of(2222),
                      AliasName.of("testAliasName2"),
                      AliasValue.of("testAliasValue2"),
                      CreatedAt.of(Instant.now()))));
      assertThat(testAliases.nextPage().valueOrNull()).isNull();
    }
  }

  @Nested
  class SizeTest {
    @Test
    void Aliases生成時に1つのAliasのListを入れた場合は返ってくるオブジェクトのsizeが2となれば良い() {
      Aliases testAliases =
          Aliases.of(
              AliasPage.of(1),
              AliasSize.of(2),
              List.of(
                  Alias.of(
                      AliasId.of(1234),
                      UserId.of(5678),
                      AliasName.of("testAliasName"),
                      AliasValue.of("testAliasValue"),
                      CreatedAt.of(
                          LocalDateTime.of(2019, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)))));
      assertThat(testAliases.size()).isEqualTo(AliasSize.of(1));
    }
  }
}
