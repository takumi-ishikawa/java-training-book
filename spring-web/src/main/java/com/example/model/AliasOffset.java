package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AliasOffset {

  private final long value;

  @Contract
  public AliasOffset(long value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static AliasOffset of(final long value) {
    return new AliasOffset(value);
  }

  public long value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AliasOffset that = (AliasOffset) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "AliasOffset{" +
            "value=" + value +
            '}';
  }
}
