package com.example.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@SuppressWarnings("WeakerAccess")
@Table(name = "aliases")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
public class AliaseEntity {

  @Id
  public final Long alias_id;
  public final Long userId;
  public final String name;
  public final String value;
  public final LocalDateTime created_at;

  public AliaseEntity(Long alias_id, final Long userId, String name, String value, LocalDateTime created_at) {
    this.alias_id = alias_id;
    this.userId = userId;
    this.name = name;
    this.value = value;
    this.created_at = created_at;
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AliaseEntity{");
    sb.append("aliasId=").append(alias_id);
    sb.append(", userId=").append(userId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", value=").append(value);
    sb.append(", createdAt=").append(created_at);
    sb.append('}');
    return sb.toString();
  }
}
