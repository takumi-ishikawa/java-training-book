package com.example.controller;

import com.example.dao.entity.UserEntity;
import com.example.json.AppJson;
import com.example.json.UserJson;
import com.example.model.User;
import com.example.model.UserId;
import com.example.model.UserName;
import com.example.model.UserToken;
import com.example.service.UserService;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @NotNull
  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Contract(pure = true)
  public UserController(
      final @NotNull UserService userService) {
    this.userService = userService;
  }

  @SuppressWarnings("MVCPathVariableInspection")
  @GetMapping(value = "{userName}", produces = "application/json")
  ResponseEntity<Object> getUser( @PathVariable("userName") final String userNameString) {
    Optional<User> user = userService.findUserByName(UserName.of(userNameString));
    return user.map(UserJson::fromUser)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(AppJson.failure("user not found")));
  }

  @SuppressWarnings("MVCPathVariableInspenction")
  @RequestMapping(produces = "application/json", method = RequestMethod.POST)
  ResponseEntity<Object> createUser( @RequestParam("userToken") String userTokenString, @RequestParam("userName") String userNameString) {
    Optional<User> user = Optional.empty();
    try {
      UserToken userToken = UserToken.of(userTokenString);
      userToken.validate();
      UserName userName = UserName.of(userNameString);
      userName.validate();
      user = userService.createUser(userToken, userName);
    } catch (IllegalArgumentException e) {
      logger.error(e.toString());
      user = Optional.empty();
    } finally {
      if (user.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("failure"));

      } else {
        return ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
      }
    }
  }
}
