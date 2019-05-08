package com.example.datastore;

import com.example.domain.User;
import com.example.domain.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapBasedUserRepository implements UserRepository {

  private final MutableMapStore store;

  public MapBasedUserRepository(final MutableMapStore store) {
    this.store = store;
  }

  @Override
  public Optional<User> findById(final int userId) {
    return store.get(userId);
  }

  @Override
  public List<User> findByIds(final int... userIds) {
    return IntStream.of(userIds)
        .mapToObj(this::findById)
        .flatMap(Optional::stream)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public User save(final User user) {
    return store.put(user);
  }
}
