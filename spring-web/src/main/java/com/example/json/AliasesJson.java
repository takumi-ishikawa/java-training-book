package com.example.json;

import com.example.model.AliasContent;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AliasesJson {

  public final Long page;
  @NotNull
  public final Long nextPage;
  @NotNull
  public final Long size;
  @NotNull
  public final List<AliasContent> contents;

  @Contract(pure = true)
  public AliasesJson(@NotNull final Long page, @NotNull final Long nextPage, @NotNull final Long size, @NotNull final List<AliasContent> contents) {
    this.page = page;
    this.nextPage = nextPage;
    this.size = size;
    this.contents = contents;
  }

  public AliasesJson(final AliasPage aliasPage, final AliasPage nextAliasPage, final AliasSize aliasSize, final List<AliasContent> contents) {
    this(aliasPage.value(), nextAliasPage.value(), aliasSize.value(), contents);
  }

  @Override
  public String toString() {
    return "AliasesJson{" +
            "page=" + page +
            ", nextPage=" + nextPage +
            ", size=" + size +
            ", contents=" + contents +
            '}';
  }
}
