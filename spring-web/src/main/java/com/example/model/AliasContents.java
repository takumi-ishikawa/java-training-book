package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AliasContents {

  @NotNull
  public final List<AliasContent> aliasContents;

  public AliasContents(@NotNull final List<AliasContent> aliasContents) {
    this.aliasContents = aliasContents;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static AliasContents of(@NotNull final List<AliasContent> aliasContents) {
    return new AliasContents(aliasContents);
  }

  public void popIfSizeOver(@NotNull final long size) {
    if (aliasContents.size() == 0) {
      return;
    }
    if (aliasContents.size() > size) {
      aliasContents.remove(aliasContents.size() - 1);
    }
  }
}
