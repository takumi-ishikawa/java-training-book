package com.example;

import com.example.dao.UserDao;
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
  CommandLineRunner commandLineRunner(UserDao userDao) {
    return args -> userDao.selectAll().forEach(System.out::println);
  }
}
