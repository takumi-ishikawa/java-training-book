package com.example.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.model.UserId;
import com.example.model.UserToken;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(DomaAutoConfiguration.class)
@ComponentScan
@Sql({
  "classpath:META-INF/com/example/dao/dropSampleTables.sql",
  "classpath:META-INF/com/example/dao/createSampleTables.sql",
  "classpath:META-INF/com/example/dao/insertSampleData.sql"
})
class UserTokenDaoTest {
  @Autowired UserTokenDao userTokenDao;

  @Test
  void UserIdが1000の場合にUserIdが1000のUserTokenEntityが返ってくる() {
    assertThat(userTokenDao.findUserTokenByUserId(UserId.of(1000L)))
        .hasValueSatisfying(
            userTokenEntity -> {
              assertThat(userTokenEntity.userId).isEqualTo(UserId.of(1000L));
            });
  }

  @Test
  void UserIdが1000の場合にそれに対応するUserTokenをもつUserTokenEntityが返ってくる() {
    assertThat(userTokenDao.findUserTokenByUserId(UserId.of(1000L)))
        .hasValueSatisfying(
            userTokenEntity -> {
              assertThat(userTokenEntity.token)
                  .isEqualTo(UserToken.of("838128c4-acb1-46ba-a3ee-4c995cb0f57c"));
            });
  }

  @Test
  void UserIdが1111の場合にemptyが返ってくる() {
    assertThat(userTokenDao.findUserTokenByUserId(UserId.of(1111L))).isEqualTo(Optional.empty());
  }
}
