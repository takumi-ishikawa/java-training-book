package com.example.json;

import com.example.model.AliasName;
import com.example.model.AliasNextPage;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import com.example.model.Aliases;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AliasesJson {

  public final Long page;
  @NotNull
  public final AliasNextPage nextPage;
  @NotNull
  public final Long size;
  @NotNull
  public final List<AliasJson> contents;

  public AliasesJson(Long page, @Nullable Long nextPage, @NotNull Long size, @NotNull List<AliasJson> contents) {
    this.page = page;
    this.nextPage = nextPage == null ? AliasNextPage.finalPage() : AliasNextPage.middlePage(nextPage);
    this.size = size;
    this.contents = contents;
  }

  public AliasesJson(AliasPage aliasPage, AliasNextPage aliasNextPage, AliasSize size, List<AliasJson> aliasJsons) {
    this.page = aliasPage.value();
    this.nextPage = aliasNextPage;
    this.size = size.value();
    this.contents = aliasJsons;
  }

  public static AliasesJson from(Aliases aliases,  Function<? super AliasName, ? extends String> uriResolver) {
    List<AliasJson> contents = aliases.aliases
            .stream()
            .limit(aliases.requestSize().value())
            .map(alias -> AliasJson.from(alias, uriResolver))
            .collect(Collectors.toUnmodifiableList());

    return new AliasesJson(aliases.page(), aliases.nextPage(), aliases.size(), contents);
  }

  @Nullable
  public Long getNextPage() {
    return nextPage.valueOrNull();
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
