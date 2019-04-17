package com.example.dao;

import com.example.entity.AliaseEntity;
import com.example.entity.UserEntity;
import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@ConfigAutowireable
@Dao
public interface AliaseDao {

    @Select
    List<AliaseEntity> selectAll();

    @Select
    List<AliaseEntity> selectAliaseByUserId(Long userId);

    @Insert
    Result<AliaseEntity> insertAliase(AliaseEntity aliaseEntity);

    @Select
    List<AliaseEntity> selectRandomAlias();
}
