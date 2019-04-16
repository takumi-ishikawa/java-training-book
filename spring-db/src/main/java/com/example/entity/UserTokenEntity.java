package com.example.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@SuppressWarnings("WeakerAccess")
@Table(name = "user_tokens")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
public class UserTokenEntity {

  @Id
  public final long userId;
  public final String token;
  public final LocalDateTime createdAt;

  public UserTokenEntity(final long userId, final String token,
      final LocalDateTime createdAt) {
    this.userId = userId;
    this.token = token;
    this.createdAt = createdAt;
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserTokenEntity{");
    sb.append("userId=").append(userId);
    sb.append(", token='").append(token).append('\'');
    sb.append(", createdAt=").append(createdAt);
    sb.append('}');
    return sb.toString();
  }
}
