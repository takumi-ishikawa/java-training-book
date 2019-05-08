package com.example.model;

import java.util.Objects;

public class AliasContent {

  private final Long aliasId;
  private final String name;
  private final String value;
  private final String url;

  public AliasContent(final Long aliasId, final String name, final String value, final String url) {
    this.aliasId = aliasId;
    this.name = name;
    this.value = value;
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AliasContent that = (AliasContent) o;
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
    return "AliasContent{" +
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
