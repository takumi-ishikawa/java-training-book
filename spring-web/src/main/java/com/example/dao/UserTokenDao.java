package com.example.dao;

import com.example.dao.entity.UserTokenEntity;
import com.example.model.UserId;
import com.example.model.UserToken;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@ConfigAutowireable
@Dao
public interface UserTokenDao {

    @Insert
    Result<UserTokenEntity> insertUserToken(final UserTokenEntity userTokenEntity);

    @Select
    Optional<UserTokenEntity> findUserTokenByUserId(final UserId userId);

    @Update
    Result<UserTokenEntity> updateUserToken(final UserTokenEntity userTokenEntity);

    @Select
    Optional<UserTokenEntity> findUserTokenEntityByUserToken(final UserToken userToken);

    @Delete
    Result<UserTokenEntity> deleteUserToken(final UserTokenEntity userTokenEntity);
}
