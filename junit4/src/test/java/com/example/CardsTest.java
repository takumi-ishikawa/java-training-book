package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CardsTest {

  @Test
  public void cutAt1st() {
    final Cards cards = Cards.of(1,2,3,4,5,6);
    final Cards actual = cards.cutAt(1);
    assertThat(actual).isEqualTo(Cards.of(2,3,4,5,6,1));
  }
}
