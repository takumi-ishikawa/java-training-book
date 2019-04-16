package com.example;

import com.example.dao.UserDao;
import com.example.service.UserService;
import java.util.UUID;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(
      UserService userService,
      RandomNameGenerator randomNameGenerator,
       UserDao userDao) {
    return args -> {
      final String name = randomNameGenerator.next();
      final UUID uuid = UUID.randomUUID();
      userService.createUser(name, uuid.toString());
      userDao.selectAll().forEach(System.out::println);
    };
  }
}
