package com.example.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AliasNextPageTest {

  @Test
  void sizeとresponseSizeが同じ場合はofが返すオブジェクトがMiddlePageであること() {
    assertThat(AliasNextPage.of(1, 2,2)).isInstanceOf(MiddlePage.class);
  }

  @Test
  void sizeとresoinseSizeが異なる場合はofが返すオブジェクトがFinalPageであること() {
    assertThat(AliasNextPage.of(1, 2, 3)).isInstanceOf(FinalPage.class);
  }

  @Test
  void AliasNextPageのBuilderが返すオブジェクトがMiddlePageであること() {
    AliasNextPage aliasNextPage = AliasNextPage.currentPage(AliasPage.of(1))
            .requestSize(AliasSize.of(2))
            .resultSize(3);
    assertThat(aliasNextPage).isInstanceOf(MiddlePage.class);
  }

  @Test
  void AliasNextPageのBuilderが返すオブジェクトがnullであること() {
    AliasNextPage aliasNextPage = AliasNextPage.currentPage(AliasPage.of(1))
            .requestSize(AliasSize.of(2))
            .resultSize(4);
    assertThat(aliasNextPage).isInstanceOf(FinalPage.class);
  }
}