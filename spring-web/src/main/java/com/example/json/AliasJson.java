package com.example.json;

import com.example.model.AliasContents;
import com.example.model.AliasNextPage;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;

public class AliasJson {

  public final Long page;
  @NotNull
  public final Long nextPage;
  @NotNull
  public final Long size;
  @NotNull
  public final AliasContents contents;

  public static class Builder {
    private Long page;
    private Long nextPage;
    private Long size;
    private AliasContents contents;

    public Builder() {}

    public Builder page(AliasPage aliasPage) {
      this.page = aliasPage.value();
      return this;
    }

    public Builder nextPage(OptionalLong aliasNextPage) {
      aliasNextPage.ifPresent(p -> nextPage = p);
      return this;
    }

    public Builder size(AliasSize aliasSize) {
      this.size = aliasSize.value();
      return this;
    }

    public Builder contents(AliasContents aliasContents) {
      this.contents = aliasContents;
      return this;
    }

    public AliasJson build() {
      return AliasJson.of(this);
    }
  }

  @Contract(pure = true)
  public AliasJson(final Builder builder) {
    this.page = builder.page;
    this.nextPage = builder.nextPage;
    this.size = builder.size;
    this.contents = builder.contents;
  }

  @NotNull
  @Contract(value = "_, _, _, _ -> new!", pure = true)
  public static AliasJson of(final Builder builder) {
    return new AliasJson(builder);
  }

  @Override
  public String toString() {
    return "AliasJson{" +
            "page=" + page +
            ", nextPage=" + nextPage +
            ", size=" + size +
            ", contents=" + contents +
            '}';
  }
}
