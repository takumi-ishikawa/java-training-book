package com.example.model;

import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AliasOffset {

  private final long value;

  @Contract(pure = true)
  AliasOffset(long value) {
    this.value = value;
  }

  @Contract
  public AliasOffset(@NotNull final long page, @NotNull final long size) {
    this.value = page * size;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static AliasOffset of(@NotNull final long page, @NotNull final long size) {
    return new AliasOffset(page, size);
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
    return "AliasOffset{" + "value=" + value + '}';
  }
}
