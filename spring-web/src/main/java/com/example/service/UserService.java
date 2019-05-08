package com.example.service;

import com.example.dao.entity.UserEntity;
import com.example.model.*;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;

public interface UserService {

  Optional<User> findUserById(@NotNull final UserId userId);

  Optional<User> findUserByName(@NotNull final UserName userName);

  Optional<User> createUser(@NotNull UserToken userToken, @NotNull UserName userName);

  Optional<User> updateUserToken(@NotNull UserToken userToken, @NotNull UserName userName);

  void deleteUserByUserNameAndUserToken(@NotNull final UserName userName, @NotNull final UserToken userToken);

  List<Alias> findAliasesByUserNameAndUserToken(@NotNull final UserName userName,
                                                @NotNull final UserToken userToken,
                                                @NotNull final AliasPage aliasPage,
                                                @NotNull final AliasSize aliasSize);
}
