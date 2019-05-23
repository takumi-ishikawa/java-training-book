package com.example.service.impl;

import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import com.example.model.Alias;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import com.example.model.Aliases;
import com.example.model.CreatedAt;
import com.example.model.IdGenerator;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserRepository;
import com.example.model.UserToken;
import com.example.service.UserService;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final Clock clock;
  private final IdGenerator idGenerator;

  @Contract(pure = true)
  public UserServiceImpl(
      @NotNull final UserRepository userRepository, Clock clock, IdGenerator idGenerator) {
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
    return userRepository.createUser(
        User.of(UserId.of(userId), userName, userToken, CreatedAt.of(created)));
  }

  @Transactional
  @Override
  public Optional<User> updateUserToken(@NotNull UserToken userToken, @NotNull UserName userName) {
    return userRepository
        .findByName(userName)
        .flatMap(u -> userRepository.updateUserToken(u, userToken));
  }

  @Transactional
  @Override
  public Optional<User> deleteUserByUserName(@NotNull final UserName userName) {
    return userRepository
        .findByName(userName)
        .map(
            u -> {
              userRepository.deleteUserTokenEntity(
                  new UserTokenEntity(u.userId, u.token, u.createdAt));
              userRepository.deleteUserEntity(new UserEntity(u.userId, u.name, u.createdAt));
              return u;
            });
  }

  @Override
  public Aliases findAliasesByUserName(
      @NotNull UserName userName,
      @NotNull AliasSize aliasSize,
      @NotNull final AliasPage aliasPage) {
    List<Alias> aliases =
        userRepository.findAliasesByUserName(
            userName, aliasSize.increment(), aliasPage.toOffset(aliasSize));
    return Aliases.of(aliasPage, aliasSize, aliases);
  }
}
