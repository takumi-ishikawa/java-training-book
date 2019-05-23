package com.example.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.dao.entity.UserEntity;
import com.example.model.CreatedAt;
import com.example.model.UserId;
import com.example.model.UserName;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration;
import org.seasar.doma.jdbc.JdbcException;
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
class UserDaoTest {

  @Autowired UserDao userDao;

  @Test
  void UserIdが1000の場合はUserNameがJohnのUserが返ってくる() {
    assertThat(userDao.findUserById(UserId.of(1000L)).get().name).isEqualTo(UserName.of("John"));
  }

  @Test
  void UserIdが1111の場合はemptyが返ってくる() {
    assertThat(userDao.findUserById(UserId.of(1111L))).isEqualTo(Optional.empty());
  }

  @Test
  void UserNameがJohnの場合はUserIdが1000のUserが返ってくる() {
    assertThat(userDao.findUserByName(UserName.of("John")).get().userId)
        .isEqualTo(UserId.of(1000L));
  }

  @Test
  void UserNameがJの場合はemptyが返ってくる() {
    assertThat(userDao.findUserByName(UserName.of("J"))).isEqualTo(Optional.empty());
  }

  //  @Test
  //  void insertできた結果としてResultが返ってくる() {
  //    UserEntity userEntity =
  //        new UserEntity(UserId.of(5000L), UserName.of("Udon"), CreatedAt.of(Instant.now()));
  //    assertThat(userDao.insertUser(userEntity)).isInstanceOf(Result.class);
  //  }

  @Test
  void すでに存在するUserEntityの場合はinsertできずに例外が発生する() {
    UserEntity userEntity =
        new UserEntity(UserId.of(5000L), UserName.of("Udon"), CreatedAt.of(Instant.now()));
    assertThrows(JdbcException.class, () -> userDao.insertUser(userEntity));
  }

  //  @Test
  //  void 存在するUserEntityの場合はdeleteできた結果としてResultが返ってくる() {
  //    UserEntity userEntity =
  //        new UserEntity(UserId.of(5000L), UserName.of("Udon"), CreatedAt.of(Instant.now()));
  //    assertThat(userDao.deleteUser(userEntity)).isInstanceOf(Result.class);
  //  }

  @Test
  void 存在しないUserEntityの場合はdeleteできずに例外が発生する() {
    UserEntity userEntity =
        new UserEntity(UserId.of(6000L), UserName.of("Soba"), CreatedAt.of(Instant.now()));
    assertThrows(JdbcException.class, () -> userDao.deleteUser(userEntity));
  }
}
