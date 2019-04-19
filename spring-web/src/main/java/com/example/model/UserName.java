package com.example.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserName {

  @NotNull
  private final String value;
  private static final int maxVauleLength = 35;
  private static final int minValueLength = 2;

  @Contract(pure = true)
  private UserName(@NotNull final String value) {
    this.value = value;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static UserName of(@NotNull final String value) throws Exception {
    if (isCorrect(value)) {
      return new UserName(value);
    } else {
      throw new Exception("Invalid UserName");
    }
  }

  private static Boolean isCorrect(String value) {
    final Pattern pattern = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9]*$");
    Matcher matcher = pattern.matcher(value);
    if (value.length() >= minValueLength && value.length() <= maxVauleLength && matcher.find()) {
      return true;
    } else {
      return false;
    }
  }

  public static Optional<UserName> from(@Nullable final String value) {
    return Optional.ofNullable(value)
        .map(value1 -> {
          try {
            return of(value1);
          } catch (Exception e) {
            e.printStackTrace();
            return null;
          }
        });
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
