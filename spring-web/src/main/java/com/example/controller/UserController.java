package com.example.controller;

import com.example.dao.entity.UserEntity;
import com.example.json.AppJson;
import com.example.json.UserJson;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.service.UserService;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @NotNull
  private final UserService userService;

  @Contract(pure = true)
  public UserController(
      final @NotNull UserService userService) {
    this.userService = userService;
  }

//  @SuppressWarnings("MVCPathVariableInspection")
//  @GetMapping(value = "{userId}", produces = "application/json")
//  ResponseEntity<Object> getUser( @PathVariable("userId") final long userIdLong) {
//    final UserId userId = UserId.of(userIdLong);
//    final Optional<User> user = userService.findUserById(userId);
//    return user.map(UserJson::fromUser)
//        .<ResponseEntity<Object>>map(ResponseEntity::ok)
//        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//            .body(AppJson.failure("user not found")));
//  }

  @SuppressWarnings("MVCPathVariableInspection")
  @GetMapping(value = "{userName}", produces = "application/json")
  ResponseEntity<Object> getUser( @PathVariable("userName") final String userNameString) {
    final UserName userName;
    try {
      userName = UserName.of(userNameString);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("BAD REQUEST"));
    }
    final Optional<User> user = userService.findUserByName(userName);
    return user.map(UserJson::fromUser)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(AppJson.failure("user not found")));
  }

  @SuppressWarnings("MVCPathVariableInspenction")
  @RequestMapping(produces = "application/json", method = RequestMethod.POST)
  ResponseEntity<Object> createUser( @RequestParam("userToken") String userToken, @RequestParam("userName") String userName) {
    final Optional<User> user = userService.createUser(userToken, userName);
    if (user.isEmpty()) {
      return ResponseEntity.unprocessableEntity().body(AppJson.failure("failure"));
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
    }
  }
}
