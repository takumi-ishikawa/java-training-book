package com.example.model;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface UserRepository {

  Optional<User> findById(@NotNull final UserId userId);
}
