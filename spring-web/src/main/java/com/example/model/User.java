package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public class User {

  @NotNull public final UserId userId;
  @NotNull public final UserName name;
  @NotNull public final UserToken token;
  @NotNull public final CreatedAt createdAt;

  @Contract(pure = true)
  private User(
      @NotNull final UserId userId,
      @NotNull final UserName name,
      @NotNull final UserToken token,
      @NotNull final CreatedAt createdAt) {
    this.userId = userId;
    this.name = name;
    this.token = token;
    this.createdAt = createdAt;
  }

  @NotNull
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static User of(
      @NotNull final UserId userId,
      @NotNull final UserName name,
      @NotNull final UserToken token,
      @NotNull final CreatedAt createdAt) {
    return new User(userId, name, token, createdAt);
  }
}
