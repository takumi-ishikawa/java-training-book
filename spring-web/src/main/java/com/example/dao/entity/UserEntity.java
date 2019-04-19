package com.example.dao.entity;

import com.example.model.CreatedAt;
import com.example.model.UserId;
import com.example.model.UserName;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@SuppressWarnings("WeakerAccess")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "users")
public class UserEntity {

  @Id
  public final UserId userId;
  public final UserName name;
  public final CreatedAt createdAt;

  public UserEntity(final @NotNull UserId userId,
      final @NotNull UserName name,
      final @NotNull CreatedAt createdAt) {
    this.userId = userId;
    this.name = name;
    this.createdAt = createdAt;
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserEntity{");
    sb.append("userId=").append(userId);
    sb.append(", name=").append(name);
    sb.append(", createdAt=").append(createdAt);
    sb.append('}');
    return sb.toString();
  }
}
