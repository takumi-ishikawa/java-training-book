package com.example.json;

import com.example.model.Alias;
import com.example.model.AliasId;
import com.example.model.AliasName;
import com.example.model.AliasValue;

import java.util.Objects;
import java.util.function.Function;

public class AliasJson {

  private final Long aliasId;
  private final String name;
  private final String value;
  private final String url;

  public AliasJson(final Long aliasId, final String name, final String value, final String url) {
    this.aliasId = aliasId;
    this.name = name;
    this.value = value;
    this.url = url;
  }

  public AliasJson(AliasId aliasId, AliasName aliasName, AliasValue alias, String url) {
    this(aliasId.value(), aliasName.value(), alias.value(), url);
  }

  public static AliasJson from(Alias alias, Function<? super AliasName, ? extends String> uriResolver) {
    return new AliasJson(alias.aliasId, alias.name, alias.value, uriResolver.apply(alias.name));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AliasJson that = (AliasJson) o;
    return Objects.equals(aliasId, that.aliasId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aliasId, name, value, url);
  }

  @Override
  public String toString() {
    return "AliasJson{" +
            "aliasId=" + aliasId +
            ", name='" + name + '\'' +
            ", value='" + value + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

  public Long getAliasId() {
    return aliasId;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getUrl() {
    return url;
  }
}
