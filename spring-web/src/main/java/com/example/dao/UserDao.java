package com.example.dao;

import com.example.dao.entity.UserDataView;
import com.example.dao.entity.UserEntity;
import com.example.model.User;
import com.example.model.UserId;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.model.UserName;
import com.example.model.UserToken;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@ConfigAutowireable
@Dao
public interface UserDao {

  @Select
  Optional<UserDataView> findUserById(final UserId userId);

  @Select
  Optional<UserDataView> findUserByName(final UserName userName);

  @Insert
  Result<UserEntity> insertUser(final UserEntity userEntity);

}
