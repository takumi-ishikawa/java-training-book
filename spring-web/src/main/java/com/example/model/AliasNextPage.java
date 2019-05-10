package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;

public class AliasNextPage {

  private final OptionalLong value;

  @NotNull
  @Contract
  public AliasNextPage(@NotNull final long page, @NotNull final long size, @NotNull final long responseSize) {
    if (size == responseSize) {
      value = OptionalLong.of(page + 1);
    } else {
      value = OptionalLong.empty();
    }
  }

  @NotNull
  @Contract(value = "_, _, _ -> new", pure = true)
  public static AliasNextPage of(final long page, final long size, final long responseSize) {
    return new AliasNextPage(page, size, responseSize);
  }

  public OptionalLong toOptionalLong() {
    return value;
  }
}
