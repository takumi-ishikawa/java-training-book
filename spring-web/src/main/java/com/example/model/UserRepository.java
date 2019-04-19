package com.example.model;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;

public interface UserRepository {

  Optional<User> findById(@NotNull final UserId userId);

  Optional<User> findByName(@NotNull final UserName userName);

  Optional<User> createUser(@NotNull final User user);

}
