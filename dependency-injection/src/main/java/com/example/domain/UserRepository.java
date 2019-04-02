package com.example.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(int userId);

  List<User> findByIds(int... userIds);

  User save(User user);
}
