package com.example.infrastructure;

import com.example.dao.AliasDao;
import com.example.dao.UserDao;
import com.example.dao.UserTokenDao;
import com.example.dao.entity.AliasDataView;
import com.example.dao.entity.UserDataView;
import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import com.example.model.Alias;
import com.example.model.AliasOffset;
import com.example.model.AliasSize;
import com.example.model.NotOneResultException;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserRepository;
import com.example.model.UserToken;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRepositoryImpl implements UserRepository {

  @NotNull
  private final UserDao userDao;
  private final UserTokenDao userTokenDao;
  private final AliasDao aliasDao;

  private final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

  @Contract(pure = true)
  public UserRepositoryImpl(@NotNull final UserDao userDao, @NotNull final UserTokenDao userTokenDao, @NotNull final AliasDao aliasDao) {
    this.userDao = userDao;
    this.userTokenDao = userTokenDao;
    this.aliasDao = aliasDao;
  }

  @Override
  public Optional<User> findById(final @NotNull UserId userId) {
    return userDao.findUserById(userId)
        .map(UserDataView::toUser);
  }

  @Override
  public Optional<User> findByName(final @NotNull UserName userName) {
    return userDao.findUserByName(userName)
            .map(UserDataView::toUser);
  }

  @Override
  public Optional<User> createUser(final User user) {
    Result<UserEntity> userEntityResult = userDao.insertUser(new UserEntity(user.userId, user.name, user.createdAt));
    if (userEntityResult.getCount() != 1) {
      throw new NotOneResultException("not one userEntityResult");
    }
    Result<UserTokenEntity> tokenEntityResult = userTokenDao.insertUserToken(new UserTokenEntity(user.userId, user.token, user.createdAt));
    if (tokenEntityResult.getCount() != 1) {
      throw new NotOneResultException("not one tokenEntityResult");
    }

    return Optional.of(user);
  }

  @Override
  public Optional<User> updateUserToken(final @NotNull User user, final@NotNull UserToken userToken) {
    Result<UserTokenEntity> userTokenEntityResult = userTokenDao.updateUserToken(new UserTokenEntity(user.userId, userToken, user.createdAt));
    if (userTokenEntityResult.getCount() != 1) {
      throw new NotOneResultException("not one userTokenEntityResult");
    }
    return Optional.of(user);
  }

  @Override
  public Optional<UserToken> findUserTokenByUserId(final @NotNull UserId userId) {
    return findById(userId)
            .flatMap(u -> userTokenDao.findUserTokenByUserId(u.userId))
              .map(t -> t.token);
  }

  @Override
  public void deleteUserEntity(@NotNull final UserEntity userEntity) {
    userDao.deleteUser(userEntity);
  }

  @Override
  public void deleteUserTokenEntity(@NotNull final UserTokenEntity userTokenEntity) {
    userTokenDao.deleteUserToken(userTokenEntity);
  }

  @Override
  public Optional<User> findUserByUserNameAndUserToken(@NotNull final UserName userName, @NotNull final UserToken userToken) {
    return findByName(userName).map(u -> {
      if (u.token.value().equals(userToken.value())) {
        return u;
      } else {
        throw new IllegalArgumentException("invalid token");
      }
    });
  }

  @Override
  public List<Alias> findAliasesByUserName(@NotNull UserName userName, @NotNull AliasSize aliasSize, @NotNull final AliasOffset aliasOffset) {
    Optional<User> user = findByName(userName);
    return aliasDao.findAliasesById(user.orElseThrow(() -> new IllegalArgumentException("invalid input")).userId, aliasSize, aliasOffset)
            .stream()
            .map(AliasDataView::toAlias)
            .collect(Collectors.toList());
  }
}
