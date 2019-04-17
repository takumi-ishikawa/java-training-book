package com.example.dao.entity;

import com.example.model.CreatedAt;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserToken;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@SuppressWarnings("WeakerAccess")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
public class UserDataView {

  public final UserId userId;
  public final UserName name;
  public final UserToken token;
  public final CreatedAt createdAt;

  @Contract(pure = true)
  public UserDataView(final @NotNull UserId userId,
      final @NotNull UserName name,
      final @NotNull UserToken token,
      final @NotNull CreatedAt createdAt) {
    this.userId = userId;
    this.name = name;
    this.token = token;
    this.createdAt = createdAt;
  }

  @NotNull
  public User toUser() {
    return User.of(userId, name, token, createdAt);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserDataView{");
    sb.append("userId=").append(userId);
    sb.append(", name=").append(name);
    sb.append(", token=").append(token);
    sb.append(", createdAt=").append(createdAt);
    sb.append('}');
    return sb.toString();
  }
}
