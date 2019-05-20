package com.example.json;

import com.example.model.CreatedAt;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import java.time.Instant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public class UserJson {

  public final long userId;
  @NotNull public final String name;
  @NotNull public final Instant createdAt;

  @Contract(pure = true)
  public UserJson(final long userId, @NotNull final String name, @NotNull final Instant createdAt) {
    this.userId = userId;
    this.name = name;
    this.createdAt = createdAt;
  }

  public UserJson(final UserId userId, final UserName name, final CreatedAt createdAt) {
    this(userId.value(), name.value(), createdAt.value());
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserJson{");
    sb.append("userId=").append(userId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", createdAt=").append(createdAt);
    sb.append('}');
    return sb.toString();
  }

  @Contract("_ -> new")
  @NotNull
  public static UserJson fromUser(@NotNull final User user) {
    return new UserJson(user.userId, user.name, user.createdAt);
  }
}
