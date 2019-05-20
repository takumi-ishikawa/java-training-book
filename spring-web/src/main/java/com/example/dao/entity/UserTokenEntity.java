package com.example.dao.entity;

import com.example.model.CreatedAt;
import com.example.model.UserId;
import com.example.model.UserToken;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@SuppressWarnings("WeakerAccess")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "user_tokens")
public class UserTokenEntity {

  @Id public final UserId userId;
  public final UserToken token;
  public final CreatedAt createdAt;

  public UserTokenEntity(final UserId userId, final UserToken token, final CreatedAt createdAt) {
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
