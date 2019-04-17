package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UserToken {

  @NotNull
  private final String value;

  @Contract(pure = true)
  private UserToken(final @NotNull String value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserToken of(final @NotNull String value) {
    return new UserToken(value);
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserToken)) {
      return false;
    }

    final UserToken userToken = (UserToken) o;

    return value.equals(userToken.value);

  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserToken{");
    sb.append("value='").append(value).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
