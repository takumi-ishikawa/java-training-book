package com.example.datastore;

import com.example.domain.User;
import com.example.domain.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapBasedUserRepository implements UserRepository {

  private final MutableMapStore store;

  @Inject
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapBasedUserRepository that = (MapBasedUserRepository) o;
    return Objects.equals(store, that.store);
  }

  @Override
  public int hashCode() {
    return Objects.hash(store);
  }
}
