package com.example.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserName {

  @NotNull
  private final String value;
  private static final int MAX_VALUE_LENGTH = 35;
  private static final int MIN_VALUE_LENGTH = 2;
  private static Pattern PATTERN = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9]*$");

  @Contract(pure = true)
  private UserName(@NotNull final String value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserName of(@NotNull final String value) throws IllegalArgumentException {
    return new UserName(value);
  }

  public void validate() {
    Matcher matcher = PATTERN.matcher(value);
    if (value.length() >= MIN_VALUE_LENGTH && value.length() <= MAX_VALUE_LENGTH && matcher.find()) {
      return;
    } else {
      throw new IllegalArgumentException("Invalid UserName");
    }
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
