package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Aliases {

  @NotNull
  public final List<Alias> aliases;

  public Aliases(@NotNull List<Alias> aliases) {
    this.aliases = aliases;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static Aliases of(@NotNull final List<Alias> aliases) {
    return new Aliases(aliases);
  }

  public long size() {
    return aliases.size();
  }
}
