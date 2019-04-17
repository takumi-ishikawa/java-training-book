package com.example.service;

import com.example.model.User;
import com.example.model.UserId;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface UserService {

  Optional<User> findUserById(@NotNull final UserId userId);
}
