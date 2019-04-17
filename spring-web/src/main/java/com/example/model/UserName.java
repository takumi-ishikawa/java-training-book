package com.example.model;

import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserName {

  @NotNull
  private final String value;

  @Contract(pure = true)
  private UserName(@NotNull final String value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserName of(@NotNull final String value) {
    return new UserName(value);
  }

  public static Optional<UserName> from(@Nullable final String value) {
    return Optional.ofNullable(value)
        .map(UserName::of);
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserName)) {
      return false;
    }

    final UserName userName = (UserName) o;

    return value.equals(userName.value);

  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserName{");
    sb.append("value='").append(value).append('\'');
    sb.append('}');
    return sb.toString();
  }

}
