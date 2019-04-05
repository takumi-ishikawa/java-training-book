package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OperationTest {

  @Test
  public void reFullShuffleはreFullShuffleを呼び出す() {
    final Operation operation = new ReFullShuffle();
    final Cards cards = CardsImpl.of(1,2,3,4);

    final var actual = operation.doOp(cards);

    assertThat(actual).isEqualTo(CardsImpl.of(1,3,2,4));
  }
}
