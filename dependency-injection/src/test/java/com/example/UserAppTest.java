package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAppTest {
    public static class Module extends AbstractModule {}

    @Test
    public void InjectによるUserAppの実行() {
        Injector injector = Guice.createInjector(new Module());
        UserApp userApp = injector.getInstance(UserApp.class);
        userApp.runApp();
    }
}