package com.example.infra;

import com.example.domain.IdGenerator;

import javax.inject.Inject;

public class IdGeneratorImpl implements IdGenerator {

  private final int[] published = new int[] { 200 };

  @Inject
  @Override
  public int createNewId() {
    return ++published[0];
  }
}
