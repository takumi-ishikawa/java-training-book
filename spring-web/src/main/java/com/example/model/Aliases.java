package com.example.model;

import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Aliases {

  @NotNull private final AliasPage page;
  @NotNull private final AliasSize size;

  @NotNull public final List<Alias> aliases;

  public Aliases(@NotNull AliasPage page, @NotNull AliasSize size, @NotNull List<Alias> aliases) {
    this.page = page;
    this.size = size;
    this.aliases = aliases;
  }

  @Contract(value = "_, _, _ -> new", pure = true)
  @NotNull
  public static Aliases of(
      @NotNull AliasPage page, @NotNull AliasSize size, @NotNull final List<Alias> aliases) {
    return new Aliases(page, size, aliases);
  }

  public AliasPage page() {
    return page;
  }

  public AliasNextPage nextPage() {
    return AliasNextPage.currentPage(page).requestSize(size).resultSize(aliases.size());
  }

  public AliasSize requestSize() {
    return size;
  }

  public AliasSize size() {
    return AliasSize.of(aliases.size());
  }
}
