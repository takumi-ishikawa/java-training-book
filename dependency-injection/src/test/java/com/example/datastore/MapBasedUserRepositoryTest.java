package com.example.datastore;

import com.example.domain.UserRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapBasedUserRepositoryTest {

    public static class Module extends AbstractModule
    {
    }

    @Test
    public void MapBasedUserRepositoryインスタンスのが正しいかどうか() {
        Injector injector = Guice.createInjector(new Module());
        MapBasedUserRepository repository= injector.getInstance(MapBasedUserRepository.class);

        assertThat(repository).isInstanceOf(MapBasedUserRepository.class);
    }

    @Test
    public void findByIdに0を入れると石田三成() {
        Injector injector = Guice.createInjector(new Module());
        MapBasedUserRepository repository = injector.getInstance(MapBasedUserRepository.class);

        assertEquals(repository.findById(0).get().name, "石田三成");
    }

    @Test
    public void findByIdに1を入れると安国寺恵瓊() {
        Injector injector = Guice.createInjector(new Module());
        MapBasedUserRepository repository = injector.getInstance(MapBasedUserRepository.class);

        assertEquals(repository.findById(1).get().name, "安国寺恵瓊");
    }

    @Test
    public void findByIdに2を入れると吉川元春() {
        Injector injector = Guice.createInjector(new Module());
        MapBasedUserRepository repository = injector.getInstance(MapBasedUserRepository.class);

        assertEquals(repository.findById(2).get().name, "吉川元春");
    }
}