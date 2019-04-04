package com.example;

import java.util.Arrays;

public class Cards {

  private final int[] cards;

  private Cards(final int[] cards) {
    this.cards = cards;
  }

  public static Cards of(final int... cards) {
    return new Cards(cards);
  }

  public static Cards twoTimes(final int number) {
    final int[] cards = new int[number * 2];
    for (int i = 0; i < (number * 2); i++) {
      cards[i] = i + 1;
    }
    return of(cards);
  }

  public Cards cutAt(final int position) {
    final int[] newCards = new int[cards.length];
    System.arraycopy(cards, 0, newCards, cards.length - position, position);
    System.arraycopy(cards, position, newCards, 0, cards.length - position);
    return new Cards(newCards);
  }

  public Cards reFullShuffle() {
    return new Cards(cards);
  }

  @Override
  public String toString() {
    return "cards[" + Arrays.toString(cards) + "]";
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(cards);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if(!(obj instanceof Cards)) {
      return false;
    }
    final Cards another = (Cards) obj;
    return Arrays.equals(this.cards, another.cards);
  }
}
