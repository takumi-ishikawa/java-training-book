package com.example.controller;

import com.example.json.AppJson;
import com.example.json.UserJson;
import com.example.model.AppException;
import com.example.model.ErrorType;
import com.example.model.User;
import com.example.model.UserId;
import com.example.service.UserService;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @SuppressWarnings("MVCPathVariableInspection")
  @GetMapping(value = "{userId}", produces = "application/json")
  ResponseEntity<Object> getUser( @PathVariable("userId") final long userIdLong) {
    final UserId userId = UserId.of(userIdLong);
    final Optional<User> user = userService.findUserById(userId);
    return user.map(UserJson::fromUser)
        .<ResponseEntity<Object>>map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(AppJson.failure("user not found")));
  }

  @GetMapping(value = "error", produces = "application/json")
  ResponseEntity<Object> error() {
    throw new AppException(ErrorType.USER_INPUT, "this is invalid uri");
  }

  @GetMapping(value = "bad", produces = "application/json")
  ResponseEntity<Object> bad() {
    throw new IllegalArgumentException();
  }
}
