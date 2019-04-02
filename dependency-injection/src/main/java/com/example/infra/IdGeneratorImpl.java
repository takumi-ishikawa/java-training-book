package com.example.infra;

import com.example.domain.IdGenerator;

public class IdGeneratorImpl implements IdGenerator {

  private final int[] published = new int[] { 200 };

  @Override
  public int createNewId() {
    return ++published[0];
  }
}
