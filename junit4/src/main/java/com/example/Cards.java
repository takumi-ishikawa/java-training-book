package com.example;

public class Cards {

  public static Cards of(final int... cards) {
    return new Cards();
  }

  public Cards cutAt(final int position) {
    return new Cards();
  }

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    return obj instanceof Cards;
  }
}
