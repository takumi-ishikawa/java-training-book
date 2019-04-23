package com.example.service;

import com.example.dao.entity.UserEntity;
import com.example.model.User;
import com.example.model.UserId;
import java.util.Optional;

import com.example.model.UserName;
import com.example.model.UserToken;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;

public interface UserService {

  Optional<User> findUserById(@NotNull final UserId userId);

  Optional<User> findUserByName(@NotNull final UserName userName);

  Optional<User> createUser(@NotNull UserToken userToken, @NotNull UserName userName);

  Optional<User> updateUserToken(@NotNull UserToken userToken, @NotNull UserName userName);

  void authorizeUser(UserToken xUserToken, UserName userName);
}
