package com.example.controller;

import com.example.App;
import com.example.controller.json.UserTokenJson;
import com.example.json.AppJson;
import com.example.json.UserJson;
import com.example.model.AppException;
import com.example.model.ErrorType;
import com.example.model.User;
import com.example.model.UserName;
import com.example.model.UserToken;
import com.example.service.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    final Optional<User> user = userService.findUserByName(UserName.of(userNameString));
    return user.map(UserJson::fromUser)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(AppJson.failure("user not found")));
  }

  @SuppressWarnings("MVCPathVariableInspenction")
  @RequestMapping(produces = "application/json", method = RequestMethod.POST)
  ResponseEntity<Object> createUser( @RequestParam("userToken") String userTokenString, @RequestParam("userName") String userNameString) {
    try {
      final UserToken userToken = UserToken.of(userTokenString);
      userToken.validate();
      final UserName userName = UserName.of(userNameString);
      userName.validate();
      return userService.createUser(userToken, userName)
              .<ResponseEntity<Object>>map(u -> ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success")))
              .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("failed to create user")));
    } catch (IllegalArgumentException e) {
      logger.error("failed to create user", e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppJson.failure("failed to create user"));
    } catch(DataAccessException e) {
      logger.error("failed to create new user", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("failed to create user"));
    }
  }

  @GetMapping(value = "error", produces = "application/json")
  ResponseEntity<Object> error() {
    throw new AppException(ErrorType.USER_INPUT, "this is invalid uri");
  }

  @GetMapping(value = "bad", produces = "application/json")
  ResponseEntity<Object> bad() {
    throw new IllegalArgumentException();
  }

  @SuppressWarnings("MVCPathVariableInspection")
  @RequestMapping(method = RequestMethod.PUT, value = "{name}", produces = "application/json", consumes = "application/json")
  ResponseEntity<Object> updateUserToken(@RequestHeader("X-USER-TOKEN") final String xUserToken, @PathVariable("name") final String userNameString, @RequestBody final UserTokenJson userTokenJson) {
    try {
      userService.findUserByName(UserName.of(userNameString));
      UserToken userToken = UserToken.of(userTokenJson.getToken());
      userToken.validate();
      UserName userName = UserName.of(userNameString);
      userName.validate();
      userService.authorizeUser(UserToken.of(xUserToken), userName);
      userService.updateUserToken(userToken, userName);
      logger.info("success : updateUserToken");
      return ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
    } catch (AppException e) {
      if (e.errorType == ErrorType.NO_RESOURCE) {
        logger.error("failed to updateUserToken : error type is NOT_FOUND", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppJson.failure("user is not found"));
      } else if (e.errorType == ErrorType.AUTHORIZATION) {
        logger.error("failed to updateUserToken : error type is AUTHORIZATION", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AppJson.failure("unauthorized"));
      } else {
        logger.error("failed to updateUserToken : error type is UNKNOWN", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("server error"));
      }
    } catch (IllegalArgumentException e) {
      logger.error("failed to updateUserToken : invalid input", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("invalid input"));
    } catch (DataAccessException e) {
      logger.error("failed to updateUserToken : Database error", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("database error"));
    }
  }

  @SuppressWarnings("MVCPathVariableInspection")
  @RequestMapping(method = RequestMethod.DELETE, value = "{name}", produces = "application/json", consumes = "application/json")
  ResponseEntity<Object> deleteUserByuserNameAndUserToken(@PathVariable("name") final String userNameString, @RequestHeader("X-USER-TOKEN") final String xUserToken) {
    try {
      final UserToken userToken = UserToken.of(xUserToken);
      userToken.validate();
      final UserName userName = UserName.of(userNameString);
      userName.validate();
      userService.deleteUserByUserNameAndUserToken(userName, userToken);
      logger.info("success : deleteUserByUserToken");
      return ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
    } catch (IllegalArgumentException e) {
      logger.error("failed to deleteUserByUserToken : invalid input", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("invalid input"));
    } catch (AppException e) {
      if (e.errorType == ErrorType.USER_INPUT) {
        logger.error("failed to deleteUserByUserToken : error type is USER_INPUT", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppJson.failure("user is not found"));
      } else {
        logger.error("failed to deleteUserByUserToken : error type is ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("server error"));
      }
    } catch (DataAccessException e) {
      logger.error("failed to deleteUserByUserToken : Database error", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("database error"));
    }
  }
}
