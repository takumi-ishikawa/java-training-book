package com.example.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AliasName {

  @NotNull
  private final String value;

  public AliasName(@NotNull String value) {
    this.value = value;
  }

  @NotNull
  public String value() {
    return value;
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
    return "AliasName{" +
            "value='" + value + '\'' +
            '}';
  }
}
