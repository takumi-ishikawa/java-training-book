package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public class Alias {

  @NotNull public final AliasId aliasId;
  @NotNull public final UserId userId;
  @NotNull public final AliasName name;
  @NotNull public final AliasValue value;
  @NotNull public final CreatedAt createdAt;

  public Alias(
      @NotNull AliasId aliasId,
      @NotNull UserId userId,
      @NotNull AliasName name,
      @NotNull AliasValue value,
      @NotNull CreatedAt createdAt) {
    this.aliasId = aliasId;
    this.userId = userId;
    this.name = name;
    this.value = value;
    this.createdAt = createdAt;
  }

  @NotNull
  @Contract(value = "_, ,__, _, _ -> new", pure = true)
  public static Alias of(
      @NotNull final AliasId aliasId,
      @NotNull final UserId userId,
      @NotNull final AliasName aliasName,
      @NotNull final AliasValue value,
      @NotNull final CreatedAt createdAt) {
    return new Alias(aliasId, userId, aliasName, value, createdAt);
  }
}
