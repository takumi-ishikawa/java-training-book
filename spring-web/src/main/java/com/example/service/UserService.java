package com.example.service;

import com.example.model.AliasPage;
import com.example.model.AliasSize;
import com.example.model.Aliases;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserToken;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface UserService {

  Optional<User> findUserById(@NotNull final UserId userId);

  Optional<User> findUserByName(@NotNull final UserName userName);

  Optional<User> createUser(@NotNull UserToken userToken, @NotNull UserName userName);

  Optional<User> updateUserToken(@NotNull UserToken userToken, @NotNull UserName userName);

  Optional<User> deleteUserByUserName(@NotNull final UserName userName);

  Aliases findAliasesByUserName(
      @NotNull final UserName userName,
      @NotNull final AliasSize aliasSize,
      @NotNull final AliasPage aliasPage);
}
