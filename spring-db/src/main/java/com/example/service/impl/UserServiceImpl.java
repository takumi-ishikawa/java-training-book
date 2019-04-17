package com.example.service.impl;

import com.example.ApplicationException;
import com.example.dao.AliaseDao;
import com.example.dao.UserDao;
import com.example.dao.UserTokenDao;
import com.example.entity.AliaseEntity;
import com.example.entity.UserEntity;
import com.example.entity.UserTokenEntity;
import com.example.repository.IdRepository;
import com.example.service.UserService;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import jdk.internal.vm.compiler.collections.EconomicMap;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final Clock clock;
  private final IdRepository idRepository;
  private final UserDao userDao;
  private final UserTokenDao userTokenDao;
  private final AliaseDao aliaseDao;

  public UserServiceImpl(final Clock clock, final IdRepository idRepository,
                         final UserDao userDao, final UserTokenDao userTokenDao, AliaseDao aliaseDao) {
    this.clock = clock;
    this.idRepository = idRepository;
    this.userDao = userDao;
    this.userTokenDao = userTokenDao;
    this.aliaseDao = aliaseDao;
  }

  @Transactional
  @Override
  public UserEntity createUser(final String name, final String token) {
    final Instant createdAt = Instant.now(clock);
    final long userId = idRepository.createId();
    final LocalDateTime created = LocalDateTime.ofInstant(createdAt, ZoneOffset.UTC);
    final UserEntity userEntity = new UserEntity(userId, name, created);
    final UserTokenEntity userTokenEntity = new UserTokenEntity(userId, token, created);
    final Result<UserEntity> userEntityResult = userDao.insertUser(userEntity);
    final Result<UserTokenEntity> userTokenEntityResult = userTokenDao
        .insertUserToken(userTokenEntity);
    if (userEntityResult.getCount() != 1 || userTokenEntityResult.getCount() != 1) {
      throw new ApplicationException("failed to save user");
    }
    return userEntity;
  }

  @Transactional
  @Override
  public List<AliaseEntity> getAliaseByUserId(final Long userId) {
    List<AliaseEntity> aliaseEntities = aliaseDao.selectAliaseByUserId(userId);
    if (aliaseEntities.size() <= 0) {
      throw new ApplicationException("failed to get aliase");
    }
    return aliaseEntities;
  }

  @Transactional
  @Override
  public AliaseEntity createAlias(final Long userId, final String name, final String value) {
    final Instant createdAt = Instant.now(clock);
    final long aliasId = idRepository.createId();
    final LocalDateTime created = LocalDateTime.ofInstant(createdAt, ZoneOffset.UTC);
    AliaseEntity aliaseEntity = new AliaseEntity(aliasId, userId, name, value, created);
    Result<AliaseEntity> aliaseEntityResult = aliaseDao.insertAliase(aliaseEntity);
    if ( aliaseEntityResult.getCount() != 1 ) {
      throw new ApplicationException("failed to save alias");
    }
    return aliaseEntity;
  }

  @Transactional
  @Override
  public List<AliaseEntity> getRandomAlias() {
    List<AliaseEntity> aliaseEntities = aliaseDao.selectRandomAlias();
    if (aliaseEntities.size() <= 0) {
      throw new ApplicationException("failed to get alias");
    }
    return aliaseEntities;
  }
}
