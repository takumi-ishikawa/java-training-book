package com.example.service;

import com.example.entity.UserEntity;
import java.time.Instant;

public interface UserService {

  UserEntity createUser(String name, String token);
}
