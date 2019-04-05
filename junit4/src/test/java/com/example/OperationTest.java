package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OperationTest {

  @Test
  public void reFullShuffleはreFullShuffleを呼び出す() {
    final Operation operation = new ReFullShuffle();
    final Cards cards = new Shuffle();

    final var actual = operation.doOp(cards);

    assertThat(actual).isInstanceOf(Shuffle.class);
  }

  @Test
  public void cutAtはcutAtを呼び出す() {
    final Operation operation = new CutAt(2);
    final Cards cards = new Shuffle();

    final Cards actual = operation.doOp(cards);

    assertThat(actual)
        .isInstanceOf(Cut.class)
        .satisfies(c -> assertThat(((Cut)c).pos).isEqualTo(2));
  }

  static class Shuffle implements Cards {

    @Override
    public Cards cutAt(final int position) {
      return new Cut(position);
    }

    @Override
    public Cards reFullShuffle() {
      return this;
    }
  }

  static class Cut implements Cards {

    final int pos;

    Cut(final int pos) {
      this.pos = pos;
    }

    @Override
    public Cards cutAt(final int position) {
      return new Cut(position);
    }

    @Override
    public Cards reFullShuffle() {
      return new Shuffle();
    }
  }
}
