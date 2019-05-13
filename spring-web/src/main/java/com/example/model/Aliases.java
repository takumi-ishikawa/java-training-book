package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  public AliasContents fromAliases(
          @NotNull final UserName userName,
          final Function<AliasName, String> toUriFunction) {
    return AliasContents.of(this.aliases.stream()
            .map(a -> new AliasContent(a.aliasId,
                    a.name,
                    a.value,
                    toUriFunction.apply(a.name)))
            .collect(Collectors.toList()));
  }

  public long size() {
    return aliases.size();
  }
}
