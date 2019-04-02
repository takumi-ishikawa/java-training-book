package com.example.service;

import com.example.UserService;
import com.example.datastore.MapBasedUserRepository;
import com.example.domain.User;
import com.example.infra.IdGeneratorImpl;
import java.util.List;

public class UserServiceImpl implements UserService {

  private final IdGeneratorImpl idGenerator;
  private final MapBasedUserRepository repository;

  public UserServiceImpl(final IdGeneratorImpl idGenerator,
      final MapBasedUserRepository repository) {
    this.idGenerator = idGenerator;
    this.repository = repository;
  }

  @Override
  public User createUser(final String name) {
    final int newId = idGenerator.createNewId();
    final User user = new User(newId, name);
    return repository.save(user);
  }

  @Override
  public List<User> findUsersByIds(final int... ids) {
    return repository.findByIds(ids);
  }
}
