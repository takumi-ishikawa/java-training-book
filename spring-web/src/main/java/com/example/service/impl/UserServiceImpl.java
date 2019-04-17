package com.example.service.impl;

import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserRepository;
import com.example.service.UserService;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @NotNull
  private final UserRepository userRepository;

  @Contract(pure = true)
  public UserServiceImpl(@NotNull final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> findUserById(final @NotNull UserId userId) {
    return userRepository.findById(userId);
  }
}
