package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AliasNextPage {

  @NotNull
  @Contract(value = "_, _, _ -> new", pure = true)
  public static AliasNextPage of(final long page, final long size, final long responseSize) {
    if (size == responseSize) {
      return new MiddlePage(page + 1);
    }
    return new FinalPage();
  }

  static AliasNextPage finalPage() {
    return new FinalPage();
  }

  static AliasNextPage middlePage(long value) {
    return new MiddlePage(value);
  }

  static AliasNextPageBuilderRequestSize currentPage(AliasPage page) {
    return aliasSize -> resultSize -> of(page.value(), aliasSize.increment().value(), resultSize);
  }

  interface AliasNextPageBuilderRequestSize {
    AliasNextPageBuilderResultSize requestSize(AliasSize aliasSize);
  }

  interface AliasNextPageBuilderResultSize {
    AliasNextPage resultSize(int resultSize);
  }

  @Nullable
  Long valueOrNull();
}

class FinalPage implements AliasNextPage {

  @Override
  public @Nullable Long valueOrNull() {
    return null;
  }
}

class MiddlePage implements AliasNextPage {

  private final long value;

  MiddlePage(long value) {
    this.value = value;
  }

  @Override
  public @NotNull Long valueOrNull() {
    return value;
  }
}