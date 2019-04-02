package com.example;

import com.example.domain.User;
import java.util.List;

public interface UserService {

  User createUser(String name);

  List<User> findUsersByIds(int... ids);
}
