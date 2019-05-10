package com.example.controller;

import com.example.controller.json.UserTokenJson;
import com.example.json.AliasesJson;
import com.example.json.AppJson;
import com.example.json.UserJson;
import com.example.model.Alias;
import com.example.model.AliasContent;
import com.example.model.AliasOffset;
import com.example.model.AliasPage;
import com.example.model.AliasSize;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    final Optional<User> user = userService.findUserByName(UserName.of(userNameString));
    return user.map(UserJson::fromUser)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(AppJson.failure("user not found")));
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
  ResponseEntity<Object> deleteUserByuserNameAndUserToken(@PathVariable("name") final String userNameString, @RequestHeader("X-USER-TOKEN") final String xUserToken) {
    final UserToken userToken = UserToken.of(xUserToken);
    userToken.validate();
    final UserName userName = UserName.of(userNameString);
    userName.validate();
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
    final AliasSize originalAliasSize = AliasSize.of(size);
    final AliasSize aliasSize = AliasSize.of(size + 1);
    final AliasOffset aliasOffset = AliasOffset.of(page * size);
    List<Alias> aliases = userService.findAliasesByUserName(userName, aliasSize, aliasOffset);
    List<AliasContent> aliasContents = aliases.stream()
            .map(a -> new AliasContent(a.aliasId.value(),
                    a.name.value(),
                    a.value.value(),
                    uriComponentsBuilder.path("/users/{name}/aliases/{alias}").build(Map.of("name", userName.value(), "alias", a.name.value())).toString()))
            .collect(Collectors.toList());
    final Long nextPage;
    if (aliases.size() == aliasSize.value()) {
      nextPage = aliasPage.value() + 1;
      aliasContents.remove(aliasContents.size() - 1);
    } else {
      nextPage = null;
    }
    AliasesJson aliasesJson = new AliasesJson(aliasPage.value(), nextPage, originalAliasSize.value(), aliasContents);
    logger.info("success : getAliases");
    return ResponseEntity.status(HttpStatus.OK).body(aliasesJson);
  }
}
