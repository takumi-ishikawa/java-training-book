package com.example.dao.converter;

import com.example.model.UserToken;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class UserTokenConverter extends AbstractConverter<UserToken, String> {

  public UserTokenConverter() {
    super(UserToken::value, value -> {
      try {
        return UserToken.of(value);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    });
  }
}
