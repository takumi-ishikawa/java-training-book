package com.example.domain;

@SuppressWarnings("WeakerAccess")
public class User {

  public final int id;
  public final String name;

  public User(final int id, final String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    @SuppressWarnings("StringBufferReplaceableByString")
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
