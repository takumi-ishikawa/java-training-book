package com.example.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@SuppressWarnings("WeakerAccess")
@Table(name = "users")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
public class UserEntity {

  @Id
  public final Long userId;
  public final String name;
  public final LocalDateTime createdAt;

  public UserEntity(final Long userId, final String name, final LocalDateTime createdAt) {
    this.userId = userId;
    this.name = name;
    this.createdAt = createdAt;
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserEntity{");
    sb.append("userId=").append(userId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", createdAt=").append(createdAt);
    sb.append('}');
    return sb.toString();
  }
}
