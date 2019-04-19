package com.example.service.impl;

import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import com.example.model.*;
import com.example.service.UserService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @NotNull
  private Logger logger = LoggerFactory.getLogger("UserServiceImpl");
  private final UserRepository userRepository;
  private final Clock clock;
  private final IdGenerator idGenerator;

  @Contract(pure = true)
  public UserServiceImpl(@NotNull final UserRepository userRepository, Clock clock, IdGenerator idGenerator) {
    this.userRepository = userRepository;
    this.clock = clock;
    this.idGenerator = idGenerator;
  }

  @Override
  public Optional<User> findUserById(final @NotNull UserId userId) {
    return userRepository.findById(userId);
  }

  @Override
  public Optional<User> findUserByName(@NotNull UserName userName) {
    return userRepository.findByName(userName);
  }

  @Transactional
  @Override
  public Optional<User> createUser(@NotNull UserToken userToken, @NotNull UserName userName) {
    final Long userId = idGenerator.createId();
    final Instant createdAt = Instant.now(clock);
    final LocalDateTime created = LocalDateTime.ofInstant(createdAt, ZoneOffset.UTC);
    return userRepository.createUser(User.of(UserId.of(userId), userName, userToken, CreatedAt.of(created)));
  }
}
