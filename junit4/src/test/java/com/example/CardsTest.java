package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CardsTest {

  @Test
  public void cutAt1st() {
    final Cards cards = CardsImpl.of(1,2,3,4,5,6);
    final Cards actual = cards.cutAt(1);
    assertThat(actual).isEqualTo(CardsImpl.of(2,3,4,5,6,1));
  }


  @Test
  public void cutAt2nd() {
    final Cards cards = CardsImpl.of(1, 2, 3, 4, 5, 6);

    final Cards actual = cards.cutAt(2);

    assertThat(actual).isEqualTo(CardsImpl.of(3,4,5,6,1,2));
  }

  @Test
  public void cutManyCardsAt() {
    final Cards cards = CardsImpl.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

    final Cards actual = cards.cutAt(9);

    assertThat(actual).isEqualTo(CardsImpl.of(10,11,12, 1,2,3,4,5,6,7,8,9));
  }

  @Test
  public void twoTimes() {
    final Cards cards = CardsImpl.twoTimes(3);

    assertThat(cards).isEqualTo(CardsImpl.of(1,2,3,4,5,6));
  }

  @Test
  public void reFullShuffle() {
    final Cards cards = CardsImpl.twoTimes(3);

    final Cards actual = cards.reFullShuffle();

    assertThat(actual).isEqualTo(CardsImpl.of(1,4,2,5,3,6));
  }

  @Test
  public void reFullShuffleOf20Cards() {
    final var cards = CardsImpl.twoTimes(10);

    final var actual = cards.reFullShuffle();

    assertThat(actual).isEqualTo(CardsImpl.of(
        1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20));
  }

  @Test
  public void equality_異なる() {
    final Cards cards = CardsImpl.of(1, 2);
    final Cards another = CardsImpl.of(2, 1);

    assertThat(cards).isNotEqualTo(another);
  }

  @Test
  public void equality_一致() {
    final Cards cards = CardsImpl.of(1, 2,3,4);
    final Cards another = CardsImpl.of(1,2,3,4);

    assertThat(cards).isEqualTo(another);
  }
}
