package com.example.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.model.AliasOffset;
import com.example.model.AliasSize;
import com.example.model.UserId;
import java.util.Collections;
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
class AliasDaoTest {

  @Autowired AliasDao aliasDao;

  @Test
  void UserIdが1000の場合に返ってきたリストの要素のUserIdが1000であると良い() {
    UserId userId = UserId.of(1000L);
    assertThat(aliasDao.findAliasesByUserId(userId, AliasSize.of(2L), AliasOffset.of(1L, 2L)))
        .allSatisfy(a -> assertThat(a.userId).isEqualTo(userId));
  }

  @Test
  void UserIdが4000かつAliasSizeが2かつAliasOffsetのpageが1かつsizeが2の場合に返ってきたリストの要素の数が2であると良い() {
    UserId userId = UserId.of(4000L);
    assertThat(
            aliasDao.findAliasesByUserId(userId, AliasSize.of(2L), AliasOffset.of(1L, 2L)).size())
        .isEqualTo(2);
  }

  @Test
  void UserIdが4000かつAliasSizeが2かつAliasOffsetのpageが2かつsizeが2の場合に返ってきたリストの要素の数が1であると良い() {
    UserId userId = UserId.of(4000L);
    assertThat(
            aliasDao.findAliasesByUserId(userId, AliasSize.of(2L), AliasOffset.of(2L, 2L)).size())
        .isEqualTo(1);
  }

  @Test
  void UserIdが4000かつAliasSizeが2かつAliasOffsetのpageが3かつsizeが2の場合に返ってきたリストが空リストであると良い() {
    UserId userId = UserId.of(4000L);
    assertThat(aliasDao.findAliasesByUserId(userId, AliasSize.of(2L), AliasOffset.of(3L, 2L)))
        .isEqualTo(Collections.EMPTY_LIST);
  }

  @Test
  void UserIdが1111の場合は空リストが返ってくる() {
    assertThat(
            aliasDao.findAliasesByUserId(
                UserId.of(1111L), AliasSize.of(2L), AliasOffset.of(1L, 2L)))
        .isEqualTo(Collections.EMPTY_LIST);
  }

  @Test
  void
      UserIdが4000かつAliasSizeが2かつAliasOffsetのpageが1かつsizeが2の場合に返ってきたリストの要素がもつAliasIdが4030から4040の範囲内であること() {
    assertThat(
            aliasDao.findAliasesByUserId(
                UserId.of(4000L), AliasSize.of(2L), AliasOffset.of(1L, 2L)))
        .allSatisfy(
            aliasDataView -> assertThat(aliasDataView.aliasId.value()).isBetween(4030L, 4040L));
  }
}
