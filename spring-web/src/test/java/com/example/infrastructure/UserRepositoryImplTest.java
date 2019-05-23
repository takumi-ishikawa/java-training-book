package com.example.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.dao.AliasDao;
import com.example.dao.UserDao;
import com.example.dao.UserTokenDao;
import com.example.dao.entity.AliasDataView;
import com.example.dao.entity.UserDataView;
import com.example.dao.entity.UserEntity;
import com.example.dao.entity.UserTokenEntity;
import com.example.model.AliasId;
import com.example.model.AliasName;
import com.example.model.AliasOffset;
import com.example.model.AliasSize;
import com.example.model.AliasValue;
import com.example.model.CreatedAt;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserToken;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.seasar.doma.jdbc.JdbcException;
import org.seasar.doma.jdbc.Result;

class UserRepositoryImplTest {
  UserRepositoryImpl userRepository;
  UserDao userDao;
  UserTokenDao userTokenDao;
  AliasDao aliasDao;

  @BeforeEach
  void setup() {
    userDao = mock(UserDao.class);
    userTokenDao = mock(UserTokenDao.class);
    aliasDao = mock(AliasDao.class);
    userRepository = new UserRepositoryImpl(userDao, userTokenDao, aliasDao);
  }

  @Nested
  class CreateUserTest {
    @Test
    void 存在しないUserの場合は例外が発生する() {
      when(userDao.insertUser(any())).thenThrow(JdbcException.class);
      assertThrows(
          JdbcException.class,
          () ->
              userRepository.createUser(
                  User.of(
                      UserId.of(1L),
                      UserName.of("testUserName"),
                      UserToken.of("testUserToken"),
                      CreatedAt.of(Instant.now()))));
    }

    @Test
    void insertに成功した場合は入力したUserと同じUserが返ってくる() {
      User user =
          User.of(
              UserId.of(1L),
              UserName.of("testUserName"),
              UserToken.of("testUserToken"),
              CreatedAt.of(Instant.now()));
      when(userDao.insertUser(any()))
          .thenReturn(new Result<>(1, new UserEntity(user.userId, user.name, user.createdAt)));
      when(userTokenDao.insertUserToken(any()))
          .thenReturn(
              new Result<>(1, new UserTokenEntity(user.userId, user.token, user.createdAt)));
      assertThat(userRepository.createUser(user)).isEqualTo(Optional.of(user));
    }
  }

  @Nested
  class UpdateUserTokenTest {
    @Test
    void 存在しないUserの場合は例外が発生する() {
      User user =
          User.of(
              UserId.of(1L),
              UserName.of("testUserName"),
              UserToken.of("testUserToken"),
              CreatedAt.of(Instant.now()));
      when(userTokenDao.updateUserToken(any())).thenThrow(JdbcException.class);
      assertThrows(
          JdbcException.class,
          () -> userRepository.updateUserToken(user, UserToken.of("testNewUserToken")));
    }

    @Test
    void updateに成功した場合は入力したUserと同じUserが返ってくる() {
      User user =
          User.of(
              UserId.of(1L),
              UserName.of("testUserName"),
              UserToken.of("testUserToken"),
              CreatedAt.of(Instant.now()));
      when(userTokenDao.updateUserToken(any()))
          .thenReturn(
              new Result<>(1, new UserTokenEntity(user.userId, user.token, user.createdAt)));
      assertThat(userRepository.updateUserToken(user, UserToken.of("testNewUserToken")))
          .isEqualTo(Optional.of(user));
    }
  }

  @Nested
  class FindUserTokenByUserIdTest {
    @Test
    void UserIdが存在しない場合はemptyが返ってくる() {
      when(userTokenDao.findUserTokenByUserId(any())).thenReturn(Optional.empty());
      assertThat(userRepository.findUserTokenByUserId(UserId.of(1111L)))
          .isEqualTo(Optional.empty());
    }

    @Test
    void トークンが見つかった場合はUserTokenが返ってくる() {
      when(userDao.findUserById(any()))
          .thenReturn(
              Optional.of(
                  new UserDataView(
                      UserId.of(1000L),
                      UserName.of("testUserName"),
                      UserToken.of("testUserToken"),
                      CreatedAt.of(Instant.now()))));
      when(userTokenDao.findUserTokenByUserId(any()))
          .thenReturn(
              Optional.of(
                  new UserTokenEntity(
                      UserId.of(1000L),
                      UserToken.of("testUserToken"),
                      CreatedAt.of(Instant.now()))));
      assertThat(userRepository.findUserTokenByUserId(UserId.of(1000L)))
          .isEqualTo(Optional.of(UserToken.of("testUserToken")));
    }
  }

  @Nested
  class DeleteUserEntityTest {
    @Test
    void ユーザーのdeleteに失敗した場合は例外が発生する() {
      when(userDao.deleteUser(any())).thenThrow(JdbcException.class);
      assertThrows(
          JdbcException.class,
          () ->
              userRepository.deleteUserEntity(
                  new UserEntity(
                      UserId.of(1000L), UserName.of("testUserName"), CreatedAt.of(Instant.now()))));
    }

    @Test
    void ユーザーのdeleteに成功した場合はなにも起きない() {
      UserEntity userEntity =
          new UserEntity(
              UserId.of(1000L), UserName.of("testUserName"), CreatedAt.of(Instant.now()));
      when(userDao.deleteUser(any())).thenReturn(new Result<>(1, userEntity));
      userRepository.deleteUserEntity(userEntity);
    }
  }

  @Nested
  class DeleteUserTokenEntityTest {
    @Test
    void トークンのdeleteに失敗した場合は例外が発生する() {
      when(userTokenDao.deleteUserToken(any())).thenThrow(JdbcException.class);
      assertThrows(
          JdbcException.class,
          () ->
              userRepository.deleteUserTokenEntity(
                  new UserTokenEntity(
                      UserId.of(1000L),
                      UserToken.of("testUserToken"),
                      CreatedAt.of(Instant.now()))));
    }

    @Test
    void トークンのdeleteに成功した場合はなにも起きない() {
      UserTokenEntity userTokenEntity =
          new UserTokenEntity(
              UserId.of(1000L), UserToken.of("testUserToken"), CreatedAt.of(Instant.now()));
      when(userTokenDao.deleteUserToken(any())).thenReturn(new Result<>(1, userTokenEntity));
      userRepository.deleteUserTokenEntity(userTokenEntity);
    }
  }

  @Nested
  class FindUserByUserNameAndUserTokenTest {
    @Test
    void 存在しないUserNameとUserTokenの場合はemptyが返ってくる() {
      when(userDao.findUserByName(any())).thenReturn(Optional.empty());
      assertThat(
              userRepository.findUserByUserNameAndUserToken(
                  UserName.of("testUserName"), UserToken.of("testUserToken")))
          .isEqualTo(Optional.empty());
    }

    @Test
    void ユーザーが見つかった場合は入力したUserNameと同じ値を持つUserが返ってくる() {
      when(userDao.findUserByName(UserName.of("testUserName")))
          .thenReturn(
              Optional.of(
                  new UserDataView(
                      UserId.of(1L),
                      UserName.of("testUserName"),
                      UserToken.of("testUserToken"),
                      CreatedAt.of(Instant.now()))));
      assertThat(
              userRepository
                  .findUserByUserNameAndUserToken(
                      UserName.of("testUserName"), UserToken.of("testUserToken"))
                  .get()
                  .name)
          .isEqualTo(UserName.of("testUserName"));
    }
  }

  @Nested
  class FindAliasesByUserNameTest {
    @Test
    void ユーザーが見つからなかった場合は例外が発生する() {
      when(aliasDao.findAliasesByUserId(UserId.of(1111L), AliasSize.of(1L), AliasOffset.of(2L, 1L)))
          .thenReturn(Collections.emptyList());
      assertThrows(
          IllegalArgumentException.class,
          () ->
              userRepository.findAliasesByUserName(
                  UserName.of("testUserName"), AliasSize.of(1L), AliasOffset.of(2L, 1L)));
    }

    @Test
    void ユーザーが見つかった場合は入力したUserNameに対応するUserIdをもつAliasのリストが返ってくる() {
      User user =
          User.of(
              UserId.of(1000L),
              UserName.of("testUserName"),
              UserToken.of("testUserToken"),
              CreatedAt.of(Instant.now()));
      AliasSize aliasSize = AliasSize.of(1L);
      AliasOffset aliasOffset = AliasOffset.of(2L, 1L);
      when(userDao.findUserByName(user.name))
          .thenReturn(
              Optional.of(new UserDataView(user.userId, user.name, user.token, user.createdAt)));
      when(aliasDao.findAliasesByUserId(user.userId, aliasSize, aliasOffset))
          .thenReturn(
              List.of(
                  new AliasDataView(
                      AliasId.of(1234L),
                      UserId.of(1000L),
                      AliasName.of("testAliasName"),
                      AliasValue.of("testAliasValue"),
                      CreatedAt.of(Instant.now()))));
      assertThat(userRepository.findAliasesByUserName(user.name, aliasSize, aliasOffset))
          .allSatisfy(a -> assertThat(a.userId).isEqualTo(user.userId));
    }
  }
}
