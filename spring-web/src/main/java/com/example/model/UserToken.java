package com.example.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UserToken {

  @NotNull private final String value;
  private static final int MAX_VALUE_LENGTH = 127;
  private static final int MIN_VALUE_LENGTH = 8;
  private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

  @Contract(pure = true)
  private UserToken(final @NotNull String value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserToken of(final @NotNull String value) {
    return new UserToken(value);
  }

  public void validate() {
    final Matcher matcher = PATTERN.matcher(value);

    if (value.length() < MIN_VALUE_LENGTH) {
      throw new IllegalArgumentException("Invalid UserToken, too short");
    }
    if (value.length() > MAX_VALUE_LENGTH) {
      throw new IllegalArgumentException("Invalid UserToken, too long");
    }
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid UserToken, not match");
    }
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
