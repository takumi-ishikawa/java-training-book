package com.example.service;

import com.example.datastore.MapBasedUserRepository;
import com.example.datastore.MutableMapStore;
import com.example.domain.User;
import com.example.infra.IdGeneratorImpl;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Test
    void createUser() {
        UserServiceImpl service = new UserServiceImpl(new IdGeneratorImpl(), new MapBasedUserRepository(new MutableMapStore()));
        User user = service.createUser("fpo");
        assertThat(user).isNotNull();
    }

    @Test
    void mutableIntObjMap() {
        MutableIntObjectMap<String> map = IntObjectMaps.mutable.empty();
        String string = map.put(1, "foo");
        assertThat(string).isNull();

        String bar = map.put(1, "bar");
        assertThat(bar).isNotNull().isEqualTo("foo");

        String baz = map.put(1, "baz");
        assertThat(baz).isNotNull().isEqualTo("bar");
    }
}
