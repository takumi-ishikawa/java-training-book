package com.example.service;

import com.example.entity.AliaseEntity;
import com.example.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

  UserEntity createUser(String name, String token);

  List<AliaseEntity> getAliaseByUserId(Long userId);

  AliaseEntity createAlias(Long userId, String name, String value);

  List<AliaseEntity> getRandomAlias();

}
