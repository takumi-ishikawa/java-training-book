package com.example.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AliasNextPageTest {

  @Test
  void pageが1でsizeが2でresponseSizeが2の場合はofが返すオブジェクトの値が3であること() {
    assertThat(AliasNextPage.of(1, 2, 2).valueOrNull()).isEqualTo(2);
  }

  @Test
  void pageが1でsizeとresoinseSizeが異なる値の場合はofが返すオブジェクトの値がnullであること() {
    assertThat(AliasNextPage.of(1, 2, 3).valueOrNull()).isNull();
  }

  @Test
  void AliasNextPageのBuilderが返すオブジェクトがMiddlePageであること() {
    AliasNextPage aliasNextPage =
        AliasNextPage.currentPage(AliasPage.of(1)).requestSize(AliasSize.of(2)).resultSize(3);
    assertThat(aliasNextPage).isInstanceOf(MiddlePage.class);
  }

  @Test
  void AliasNextPageのBuilderが返すオブジェクトがFinalPageであること() {
    AliasNextPage aliasNextPage =
        AliasNextPage.currentPage(AliasPage.of(1)).requestSize(AliasSize.of(2)).resultSize(4);
    assertThat(aliasNextPage).isInstanceOf(FinalPage.class);
  }
}
