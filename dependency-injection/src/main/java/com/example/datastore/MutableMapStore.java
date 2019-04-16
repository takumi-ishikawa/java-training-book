package com.example.datastore;

import com.example.domain.User;
import java.util.Optional;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;

public class MutableMapStore {

  private final MutableIntObjectMap<User> map;

  public MutableMapStore() {
    map = IntObjectMaps.mutable.empty();
    map.put(0, new User(0, "石田三成"));
    map.put(1, new User(1, "安国寺恵瓊"));
    map.put(2, new User(2, "吉川元春"));
  }


  Optional<User> get(final int userId) {
    return Optional.ofNullable(map.get(userId));
  }

  User put(final User user) {
    map.put(user.id, user);
    return user;
  }
}
