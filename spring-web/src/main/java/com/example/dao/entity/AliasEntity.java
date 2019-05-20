package com.example.dao.entity;

import com.example.model.AliasId;
import com.example.model.AliasValue;
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
@Table(name = "aliases")
public class AliasEntity {

  @Id public final AliasId aliasId;
  public final UserId userId;
  public final UserName name;
  public final AliasValue value;
  public final CreatedAt createdAt;

  public AliasEntity(
      @NotNull final AliasId aliasId,
      @NotNull final UserId userId,
      @NotNull final UserName name,
      @NotNull final AliasValue value,
      @NotNull final CreatedAt createdAt) {
    this.aliasId = aliasId;
    this.userId = userId;
    this.name = name;
    this.value = value;
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "AliasEntity{"
        + "aliasId="
        + aliasId
        + ", userId="
        + userId
        + ", name="
        + name
        + ", aliasValue="
        + value
        + ", createdAt="
        + createdAt
        + '}';
  }
}
