package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  public static AliasContents fromAliases(@NotNull final Aliases aliases, @NotNull final UserName userName, @NotNull final UriComponentsBuilder uriComponentsBuilder) {
    return AliasContents.of(aliases.aliases.stream()
            .map(a -> new AliasContent(a.aliasId.value(),
                    a.name.value(),
                    a.value.value(),
                    uriComponentsBuilder
                            .build(Map.of("name", userName.value(), "alias", a.name.value()))
                            .toString()))
            .collect(Collectors.toList()));
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
