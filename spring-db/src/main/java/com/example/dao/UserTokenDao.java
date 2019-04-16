package com.example.dao;

import com.example.entity.UserTokenEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@ConfigAutowireable
@Dao
public interface UserTokenDao {

  @Insert()
  Result<UserTokenEntity> insertUserToken(UserTokenEntity userTokenEntity);
}
