package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UserId {

  private final long value;

  @Contract(pure = true)
  private UserId(final long value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserId of(final long value) {
    return new UserId(value);
  }

  public long value() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserId)) {
      return false;
    }

    final UserId userId = (UserId) o;

    return value == userId.value;

  }

  @Override
  public int hashCode() {
    return (int) (value ^ (value >>> 32));
  }

  @Override
  @SuppressWarnings("StringBufferReplaceableByString")
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserId{");
    sb.append("value=").append(value);
    sb.append('}');
    return sb.toString();
  }
}
