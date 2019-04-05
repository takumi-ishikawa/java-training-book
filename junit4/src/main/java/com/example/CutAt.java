package com.example;

public class CutAt implements Operation {

  private final int position;

  public CutAt(final int position) {
    this.position = position;
  }

  @Override
  public Cards doOp(final Cards cards) {
    return cards.cutAt(position);
  }
}
