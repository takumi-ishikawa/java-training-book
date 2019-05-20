package com.example.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CreatedAt {

  @NotNull private final Instant instant;

  @Contract(pure = true)
  private CreatedAt(@NotNull final Instant instant) {
    this.instant = instant;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static CreatedAt of(@NotNull final Instant instant) {
    return new CreatedAt(instant);
  }

  @NotNull
  public static CreatedAt of(@NotNull final LocalDateTime localDateTime) {
    final Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
    return of(instant);
  }

  @NotNull
  public Instant value() {
    return instant;
  }

  @NotNull
  public LocalDateTime asLocalDateTime() {
    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CreatedAt)) {
      return false;
    }

    final CreatedAt createdAt = (CreatedAt) o;

    return instant.equals(createdAt.instant);
  }

  @Override
  public int hashCode() {
    return instant.hashCode();
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CreatedAt{");
    sb.append("instant=").append(instant);
    sb.append('}');
    return sb.toString();
  }
}
