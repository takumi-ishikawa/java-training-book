package com.example.infrastructure;

import com.example.dao.UserDao;
import com.example.dao.UserTokenDao;
import com.example.dao.entity.UserDataView;
import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import com.example.model.AppException;
import com.example.model.ErrorType;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {

  @NotNull
  private final UserDao userDao;
  private final UserTokenDao userTokenDao;

  private final Logger logger = LoggerFactory.getLogger("UserRepositoryImpl");

  @Contract(pure = true)
  public UserRepositoryImpl(@NotNull final UserDao userDao, UserTokenDao userTokenDao) {
    this.userDao = userDao;
    this.userTokenDao = userTokenDao;
  }

  @Override
  public Optional<User> findById(final @NotNull UserId userId) {
    return userDao.findUserById(userId)
        .map(UserDataView::toUser);
  }

  @Override
  public Optional<User> findByName(final @NotNull UserName userName) {
    Optional<User> user = userDao.findUserByName(userName)
            .map(UserDataView::toUser);
    if (user.isEmpty()) {
      throw new AppException(ErrorType.NO_RESOURCE, "user is not found.");
    } else {
      return user;
    }
  }

  @Transactional
  @Override
  public Optional<User> createUser(final User user) {
    try {
      Result<UserEntity> userEntityResult = userDao.insertUser(new UserEntity(user.userId, user.name, user.createdAt));
      Result<UserTokenEntity> tokenEntityResult = userTokenDao.insertUserToken(new UserTokenEntity(user.userId, user.token, user.createdAt));
    } catch(DataAccessException e) {
      logger.error("create new user", e);
      return Optional.empty();
    }

    return Optional.of(user);
  }

  @Override
  public Optional<User> updateUserToken(final @NotNull User user, final@NotNull UserToken userToken) {
    Result<UserTokenEntity> userTokenEntityResult = userTokenDao.updateUserToken(new UserTokenEntity(user.userId, userToken, user.createdAt));
    return Optional.of(user);
  }

  @Override
  public Optional<UserToken> findUserTokenByUserId(final @NotNull UserId userId) {
    return findById(userId)
            .flatMap(u -> userTokenDao.findUserTokenByUserId(u.userId)
              .map(t -> UserToken.of(t.token.value())));
  }

  @Override
  public void deleteUserEntity(UserEntity userEntity) {
    userDao.deleteUser(userEntity);
  }

  @Override
  public void deleteUserTokenEntity(UserTokenEntity userTokenEntity) {
    userTokenDao.deleteUserToken(userTokenEntity);
  }

  @Override
  public Optional<UserTokenEntity> findUserTokenEntityByUserToken(UserToken userToken) {
    return userTokenDao.findUserTokenEntityByUserToken(userToken);
  }
}
