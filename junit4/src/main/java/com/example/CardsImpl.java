package com.example;

import java.util.Arrays;

public class CardsImpl implements Cards {

  private final int[] cards;

  private CardsImpl(final int[] cards) {
    this.cards = cards;
  }

  public static Cards of(final int... cards) {
    return new CardsImpl(cards);
  }

  public static Cards twoTimes(final int number) {
    final int[] cards = new int[number * 2];
    for (int i = 0; i < (number * 2); i++) {
      cards[i] = i + 1;
    }
    return of(cards);
  }

  @Override
  public Cards cutAt(final int position) {
    final int[] newCards = new int[cards.length];
    System.arraycopy(cards, 0, newCards, cards.length - position, position);
    System.arraycopy(cards, position, newCards, 0, cards.length - position);
    return new CardsImpl(newCards);
  }

  @Override
  public Cards reFullShuffle() {
    final int[] newCards = new int[cards.length];
    final int number = cards.length / 2;
    final int[] tops = Arrays.copyOf(cards, number);
    final int[] bottoms = Arrays.copyOfRange(cards, number, number * 2);
    for (int i = 0; i < number; i++) {
      newCards[i * 2] = tops[i];
      newCards[i * 2 + 1] = bottoms[i];
    }
    return new CardsImpl(newCards);
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
    if(!(obj instanceof CardsImpl)) {
      return false;
    }
    final CardsImpl another = (CardsImpl) obj;
    return Arrays.equals(this.cards, another.cards);
  }
}
