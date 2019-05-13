package com.example.model;

import com.example.json.AliasJson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AliasContents {

  @NotNull
  public final List<AliasJson> aliasJsons;

  public AliasContents(@NotNull final List<AliasJson> aliasJsons) {
    this.aliasJsons = aliasJsons;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static AliasContents of(@NotNull final List<AliasJson> aliasJsons) {
    return new AliasContents(aliasJsons);
  }

  public void popIfSizeOver(@NotNull final long size) {
    if (aliasJsons.size() == 0) {
      return;
    }
    if (aliasJsons.size() > size) {
      aliasJsons.remove(aliasJsons.size() - 1);
    }
  }
}
