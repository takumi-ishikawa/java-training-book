package com.example.dao.entity;

import com.example.model.Alias;
import com.example.model.AliasId;
import com.example.model.AliasName;
import com.example.model.AliasValue;
import com.example.model.CreatedAt;
import com.example.model.UserId;
import com.example.model.UserName;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@SuppressWarnings("WearkerAccess")
@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
public class AliasDataView {

    public final AliasId aliasId;
    public final UserId userId;
    public final AliasName name;
    public final AliasValue value;
    public final CreatedAt createdAt;


    @Contract(pure = true)
    public AliasDataView(@NotNull final AliasId aliasId,
                       @NotNull final UserId userId,
                       @NotNull final AliasName name,
                       @NotNull final AliasValue value,
                       @NotNull final CreatedAt createdAt) {
        this.aliasId = aliasId;
        this.userId = userId;
        this.name = name;
        this.value = value;
        this.createdAt = createdAt;
    }

    @NotNull
    public Alias toAlias() {
        return Alias.of(aliasId, userId, name, value, createdAt);
    }

    @Override
    public String toString() {
        return "AliasDataView{" +
                "aliasId=" + aliasId +
                ", userId=" + userId +
                ", name=" + name +
                ", aliasValue=" + value +
                ", createdAt=" + createdAt +
                '}';
    }
}
