package com.example.controller;

import com.example.controller.json.UserTokenJson;
import com.example.json.AliasesJson;
import com.example.json.AppJson;
import com.example.json.UserJson;
import com.example.model.AliasName;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
import com.example.model.Aliases;
import com.example.model.AppException;
import com.example.model.ErrorType;
import com.example.model.NoResourceException;
import com.example.model.UserName;
import com.example.model.UserToken;
import com.example.service.UserService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.function.Function;

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
  @GetMapping(value = "{name}", produces = "application/json")
  ResponseEntity<Object> getUser( @PathVariable("name") final String userNameString) {
    return userService.findUserByName(UserName.of(userNameString))
            .map(UserJson::fromUser)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElseThrow(() -> new NoResourceException("user is not found"));
  }

  @SuppressWarnings("MVCPathVariableInspenction")
  @RequestMapping(produces = "application/json", method = RequestMethod.POST)
  ResponseEntity<Object> createUser( @RequestParam("userToken") String userTokenString, @RequestParam("name") String userNameString) {
    final UserToken userToken = UserToken.of(userTokenString);
    userToken.validate();
    final UserName userName = UserName.of(userNameString);
    userName.validate();
    return userService.createUser(userToken, userName)
            .<ResponseEntity<Object>>map(u -> ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success")))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppJson.failure("failed to create user")));
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
  ResponseEntity<Object> updateUserToken(@PathVariable("name") final String userNameString, @RequestBody final UserTokenJson userTokenJson) {
    final UserToken userToken = UserToken.of(userTokenJson.getToken());
    userToken.validate();
    final UserName userName = UserName.of(userNameString);
    userName.validate();
    userService.updateUserToken(userToken, userName);
    logger.info("success : updateUserToken");
    return ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
  }

  @SuppressWarnings("MVCPathVariableInspection")
  @RequestMapping(method = RequestMethod.DELETE, value = "{name}", produces = "application/json", consumes = "application/json")
  ResponseEntity<Object> deleteUserByuserNameAndUserToken(@PathVariable("name") final String userNameString) {
    final UserName userName = UserName.of(userNameString);
    userService.deleteUserByUserName(userName);
    logger.info("success : deleteUserByUserToken");
    return ResponseEntity.status(HttpStatus.OK).body(AppJson.success("success"));
  }

  @SuppressWarnings("MVCPathVariableInspection")
  @GetMapping(value = "{name}/aliases", produces = "application/json", consumes = "application/json")
  ResponseEntity<Object> getAliases(@PathVariable("name") final String userNameString,
                                    @RequestParam(defaultValue = "0") final Long page,
                                    @RequestParam(defaultValue = "10") final Long size,
                                    UriComponentsBuilder uriComponentsBuilder) {
    final UserName userName = UserName.of(userNameString);
    final AliasPage aliasPage = AliasPage.of(page);
    final AliasSize aliasSize = AliasSize.of(size);
    final Aliases aliases = userService.findAliasesByUserName(userName, aliasSize, aliasPage);

    final Function<AliasName, String> toResourceUri = aliasName ->
            uriComponentsBuilder.path("/users/{name}/aliases/{alias}")
                    .build(Map.of("name", userName.value(), "alias", aliasName.value()))
                    .toASCIIString();

    final AliasesJson json = AliasesJson.from(aliases, toResourceUri);
    logger.info("success : getAliases");
    return ResponseEntity.status(HttpStatus.OK).body(json);
  }
}
