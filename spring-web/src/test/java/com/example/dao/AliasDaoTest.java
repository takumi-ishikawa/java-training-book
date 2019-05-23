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
  "classpath:META-INF/com/example/dao/deleteSampleTables.sql",
  "classpath:META-INF/com/example/dao/createSampleTables.sql",
  "classpath:META-INF/com/example/dao/insertSampleData.sql"
})
class AliasDaoTest {

  @Autowired AliasDao aliasDao;

  @Test
  void UserId1000の場合に返ってきたリストの要素のUserIdが1000であると良い() {
    UserId userId = UserId.of(1000L);
    assertThat(aliasDao.findAliasesById(userId, AliasSize.of(1L), AliasOffset.of(1L, 2L)))
        .allSatisfy(a -> assertThat(a.userId).isEqualTo(userId));
  }

  @Test
  void UserIdが1111の場合は空リストが返ってくる() {
    assertThat(aliasDao.findAliasesById(UserId.of(1111L), AliasSize.of(1L), AliasOffset.of(1L, 2L)))
        .isEqualTo(Collections.emptyList());
  }
}
