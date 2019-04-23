package com.example.service.impl;

import com.example.model.AppException;
import com.example.model.CreatedAt;
import com.example.model.ErrorType;
import com.example.model.IdGenerator;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserRepository;
import com.example.model.UserToken;
import com.example.service.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

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

  @Transactional
  @Override
  public Optional<User> updateUserToken(@NotNull UserToken userToken, @NotNull UserName userName) {
    return userRepository.findByName(userName).flatMap(u -> userRepository.updateUserToken(u, userToken));
  }

  @Override
  public void authorizeUser(UserToken xUserToken, UserName userName) {
    userRepository.findByName(userName).ifPresent(u -> {
      userRepository.findUserTokenByUserId(u.userId)
              .ifPresent(t -> {
                logger.error("TEST¥t" + t.value());
                logger.error("TEST¥t" + xUserToken.value());
                if (!t.value().equals(xUserToken.value())) {
                  throw new AppException(ErrorType.AUTHORIZATION, "invalid user token");
                }
              });
    });
  }
}
