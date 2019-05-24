package com.example.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.model.Alias;
import com.example.model.AliasId;
import com.example.model.AliasName;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import com.example.model.AliasValue;
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
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

  Clock fixed = Clock.fixed(Instant.now(), ZoneId.of("UTC"));
  IdGenerator idGenerator = () -> 200L;

  UserRepository userRepository;
  UserService userService;

  @BeforeEach
  void setup() {
    userRepository = mock(UserRepository.class);
    userService = new UserServiceImpl(userRepository, fixed, idGenerator);
  }

  @Nested
  class CreateUserTest {
    @Test
    void 存在しないUserTokenとUserNameを入力した場合はemptyが返ってくる() {
      when(userRepository.createUser(any())).thenReturn(Optional.empty());
      userService
          .createUser(UserToken.of("testUserToken"), UserName.of("testUserName"))
          .ifPresent(u -> fail());
    }

    @Test
    void 入力と返り値のUserのuserTokenとuserNameが一致していると正しい() {
      User testUser =
          User.of(
              UserId.of(1L),
              UserName.of("testUserName"),
              UserToken.of("testUserToken"),
              CreatedAt.of(Instant.now()));
      when(userRepository.createUser(any())).thenReturn(Optional.of(testUser));
      userService
          .createUser(UserToken.of("testUserToken"), UserName.of("testUserName"))
          .ifPresentOrElse(
              u -> {
                assertThat(u.token).isEqualTo(testUser.token);
                assertThat(u.name).isEqualTo(testUser.name);
              },
              Assertions::fail);
    }
  }

  @Nested
  class UpdateUserTokenTest {
    @Test
    void 存在しないUserTokenとUserNameを入力するとemptyが返ってくる() {
      when(userRepository.updateUserToken(any(), any())).thenReturn(Optional.empty());
      userService
          .updateUserToken(UserToken.of("testUserToken"), UserName.of("testUserName"))
          .ifPresent(u -> fail());
    }

    @Test
    void 入力と返り値のUserのUserTokenとUserNameが一致していると正しい() {
      UserToken userToken = UserToken.of("testUserToken");
      UserName userName = UserName.of("testUserName");
      when(userRepository.findByName(userName))
          .thenReturn(
              Optional.of(
                  User.of(UserId.of(1L), userName, userToken, CreatedAt.of(Instant.now()))));
      when(userRepository.updateUserToken(any(), any()))
          .thenReturn(
              Optional.of(
                  User.of(UserId.of(1L), userName, userToken, CreatedAt.of(Instant.now()))));
      userService
          .updateUserToken(UserToken.of("testUserToken"), UserName.of("testUserName"))
          .ifPresentOrElse(
              u -> {
                assertThat(u.token).isEqualTo(userToken);
                assertThat(u.name).isEqualTo(userName);
              },
              Assertions::fail);
    }
  }

  @Nested
  class DeleteUserByUserNameTest {
    @Test
    void ユーザが見つからない場合は例外が発生する() {
      when(userRepository.findByName(any())).thenThrow(IllegalArgumentException.class);
      assertThrows(
          IllegalArgumentException.class,
          () -> userService.deleteUserByUserName(UserName.of("testUser")));
    }

    @Test
    void ユーザが見つかった場合は入力したUserNameと同じ値をもつUserを返す() {
      UserName testUserName = UserName.of("testUserName");
      when(userRepository.findByName(testUserName))
          .thenReturn(
              Optional.of(
                  User.of(
                      UserId.of(1L),
                      testUserName,
                      UserToken.of("abc1234567890"),
                      CreatedAt.of(Instant.now()))));
      assertThat(userService.deleteUserByUserName(testUserName).get().name)
          .isEqualTo(UserName.of("testUserName"));
    }
  }

  @Nested
  class FindAliasesByUserNameTest {
    @Test
    void ユーザーが見つからない場合は例外が発生する() {
      when(userRepository.findAliasesByUserName(any(), any(), any()))
          .thenThrow(IllegalArgumentException.class);
      assertThrows(
          IllegalArgumentException.class,
          () -> {
            userService.findAliasesByUserName(
                UserName.of("testUserName"), AliasSize.of(1L), AliasPage.of(2L));
          });
    }

    @Test
    void ユーザーが見つかった場合は入力したUserNameに対応するUserIdをもつAliasesを返す() {
      AliasPage aliasPage = AliasPage.of(1L);
      AliasSize aliasSize = AliasSize.of(2L);
      Alias alias =
          Alias.of(
              AliasId.of(1L),
              UserId.of(1000L),
              AliasName.of("testAliasName"),
              AliasValue.of("testAliasValue"),
              CreatedAt.of(Instant.now()));
      List<Alias> aliasList = List.of(alias);
      when(userRepository.findAliasesByUserName(any(), any(), any())).thenReturn(aliasList);
      assertThat(
              userService.findAliasesByUserName(UserName.of("testUserName"), aliasSize, aliasPage)
                  .aliases)
          .allSatisfy(a -> assertThat(a.userId).isEqualTo(UserId.of(1000L)));
    }
  }
}
