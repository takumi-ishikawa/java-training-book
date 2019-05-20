package com.example.model;

import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AliasName {

  @NotNull private final String value;

  private AliasName(@NotNull String value) {
    this.value = value;
  }

  @NotNull
  public String value() {
    return value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static AliasName of(@NotNull final String value) {
    return new AliasName(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AliasName aliasName = (AliasName) o;
    return value.equals(aliasName.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "AliasName{" + "value='" + value + '\'' + '}';
  }
}
