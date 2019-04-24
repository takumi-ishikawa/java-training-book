package com.example.controller;

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

import java.io.IOException;
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
    Optional<User> user = Optional.empty();
    try {
      final UserToken userToken = UserToken.of(userTokenString);
      userToken.validate();
      final UserName userName = UserName.of(userNameString);
      userName.validate();
      user = userService.createUser(userToken, userName);
      logger.info("success : createUserSuccess");
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
  ResponseEntity<Object> updateUserToken(@RequestHeader("X-USER-TOKEN") final String xUserToken, @PathVariable("name") final String userNameString, @RequestBody final UserTokenJson userTokenJson) throws IOException {
    Optional<User> user = Optional.empty();
    ResponseEntity<Object> responseEntity = null;
    try {
      userService.findUserByName(UserName.of(userNameString));
      UserToken userToken = UserToken.of(userTokenJson.getToken());
      userToken.validate();
      UserName userName = UserName.of(userNameString);
      userName.validate();
      userService.authorizeUser(UserToken.of(xUserToken), userName);
      user = userService.updateUserToken(userToken, userName);
      logger.info("success : updateUserToken");
      responseEntity = ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
    } catch (AppException e) {
      if (e.errorType == ErrorType.NO_RESOURCE) {
        logger.error("error type is NOT_FOUND", e);
        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppJson.failure("failure"));
      } else if (e.errorType == ErrorType.AUTHORIZATION) {
        logger.error("error type is AUTHORIZATION", e);
        responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AppJson.failure("failure"));
      }
    } catch (IllegalArgumentException e) {
      logger.error("invalid input", e);
      responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("failure"));
    } catch (DataAccessException e) {
      logger.error("Database error", e);
      responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("failure"));
    }
    return responseEntity;
  }

  @SuppressWarnings("MVCPathVariableInspection")
  @RequestMapping(method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
  ResponseEntity<Object> deleteUserByUserToken(@RequestHeader("X-USER-TOKEN") final String xUserToken) {
    ResponseEntity<Object> responseEntity = null;
    try {
      UserToken userToken = UserToken.of(xUserToken);
      userToken.validate();
      userService.deleteUserByUserToken(userToken);
      responseEntity = ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
    } catch (IllegalArgumentException e) {
      logger.error("invalid input", e);
      responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("failure"));
    } catch (AppException e) {
      if (e.errorType == ErrorType.USER_INPUT) {
        logger.error("error type is USER_INPUT", e);
        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppJson.failure("failure"));
      } else {
        logger.error("error type is ", e);
        responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("failure"));
      }
    } catch (DataAccessException e) {
      logger.error("Database error", e);
      responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AppJson.failure("failure"));
    }
    return responseEntity;
  }
}
