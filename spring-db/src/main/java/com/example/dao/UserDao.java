package com.example.dao;

import com.example.entity.UserEntity;
import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@ConfigAutowireable
@Dao
public interface UserDao {

  @Select
  List<UserEntity> selectAll();

  @Insert
  Result<UserEntity> insertUser(UserEntity userEntity);
}
