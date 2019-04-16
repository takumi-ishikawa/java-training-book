package com.example;

import com.example.datastore.MapBasedUserRepository;
import com.example.domain.IdGenerator;
import com.example.domain.UserRepository;
import com.example.infra.IdGeneratorImpl;
import com.example.service.TimeServiceImpl;
import com.example.service.UserServiceImpl;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TimeService.class).to(TimeServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(IdGenerator.class).to(IdGeneratorImpl.class);
        bind(UserRepository.class).to(MapBasedUserRepository.class);
    }
}
