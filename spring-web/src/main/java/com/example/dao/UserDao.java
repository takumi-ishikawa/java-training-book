package com.example.dao;

import com.example.dao.entity.UserDataView;
import com.example.dao.entity.UserEntity;
import com.example.model.UserId;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface UserDao {

  @Select
  Optional<UserDataView> findUserById(final UserId userId);
}
