package com.example.json;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public class AppJson {

  public final boolean success;
  @NotNull
  public final String message;

  @Contract(pure = true)
  private AppJson(final boolean success, @NotNull final String message) {
    this.success = success;
    this.message = message;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static AppJson success(@NotNull final String message) {
    return new AppJson(true, message);
  }

  public static AppJson failure(@NotNull final String message) {
    return new AppJson(false, message);
  }
}
