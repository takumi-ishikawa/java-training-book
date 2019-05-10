package com.example.model;

import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(@NotNull final UserId userId);

  Optional<User> findByName(@NotNull final UserName userName);

  Optional<User> createUser(@NotNull final User user);

  Optional<User> updateUserToken(@NotNull final User user, final @NotNull UserToken userToken);

  Optional<UserToken> findUserTokenByUserId(@NotNull final UserId userId);

  void deleteUserTokenEntity(@NotNull final UserTokenEntity userTokenEntity);

  void deleteUserEntity(@NotNull UserEntity userEntity);

  Optional<User> findUserByUserNameAndUserToken(@NotNull final UserName userName, @NotNull final UserToken userToken);

  Aliases findAliasesByUserName(@NotNull final UserName userName, @NotNull final AliasSize aliasSize, @NotNull final AliasOffset aliasOffset);
}
