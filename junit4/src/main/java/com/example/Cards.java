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

  public Cards cutAt(final int position) {
    return new Cards(this.cards);
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
