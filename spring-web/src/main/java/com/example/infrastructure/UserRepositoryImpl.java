package com.example.infrastructure;

import com.example.dao.UserDao;
import com.example.dao.entity.UserDataView;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserRepository;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

  @NotNull
  private final UserDao userDao;

  @Contract(pure = true)
  public UserRepositoryImpl(@NotNull final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<User> findById(final @NotNull UserId userId) {
    return userDao.findUserById(userId)
        .map(UserDataView::toUser);
  }
}
