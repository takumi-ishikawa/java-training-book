package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserToken {

  @NotNull
  private final String value;
  private static final int maxValueLength = 127;
  private static final int minValueLength = 8;

  @Contract(pure = true)
  private UserToken(final @NotNull String value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserToken of(final @NotNull String value) throws Exception {
    if (isCorrect(value)) {
      return new UserToken(value);
    } else {
      throw new Exception("Invalid UserToken");
    }
  }

  private static Boolean isCorrect(String value) {
    final Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
    final Matcher matcher = pattern.matcher(value);
    if (value.length() >= minValueLength && value.length() <= maxValueLength && matcher.find()) {
      return true;
    } else {
      return false;
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
