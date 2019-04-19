package com.example.infrastructure;

import com.example.dao.UserDao;
import com.example.dao.UserTokenDao;
import com.example.dao.entity.UserDataView;
import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import com.example.model.*;

import java.time.LocalDateTime;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public Optional<User> findByName(@NotNull UserName userName) {
    return userDao.findUserByName(userName)
            .map(UserDataView::toUser);
  }

  @Transactional
  @Override
  public Optional<User> createUser(User user) {
    try {
      Result<UserEntity> userEntityResult = userDao.insertUser(new UserEntity(user.userId, user.name, user.createdAt));
      Result<UserTokenEntity> tokenEntityResult = userTokenDao.insertUserToken(new UserTokenEntity(user.userId, user.token, user.createdAt));
    } catch(DataAccessException e) {
      logger.error(e.toString());
      return Optional.empty();
    }

    return Optional.of(user);
  }

}
