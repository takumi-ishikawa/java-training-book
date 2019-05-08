package com.example.dao;

import com.example.dao.entity.AliasDataView;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import com.example.model.UserId;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@ConfigAutowireable
@Dao
public interface AliasDao {

  @Select
  List<AliasDataView> findAliasesById(final UserId userId, final AliasPage aliasPage, final AliasSize aliasSize);

}
