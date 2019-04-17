package com.example;

import com.example.dao.AliaseDao;
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
      //UserDao userDao) {
      AliaseDao aliaseDao) {
    return args -> {
//      // sample
//      final String name = randomNameGenerator.next();
//      final UUID uuid = UUID.randomUUID();
//      userService.createUser(name, uuid.toString());
//      userDao.selectAll().forEach(System.out::println);
      // 課題１
      //final Long userId = 3000L;
      //userService.getAliaseByUserId(userId);
      //aliaseDao.selectAliaseByUserId(userId).forEach(System.out::println);
//      // 課題２
//      final Long userId = 3000L;
//      final String name = "FooFooFoo";
//      final String value = "BarBarBar";
//      userService.createAlias(userId, name, value);
//      aliaseDao.selectAll().forEach(System.out::println);
      // 発展（ランダムレコード取得）
      userService.getRandomAlias().forEach(System.out::println);
    };
  }
}
