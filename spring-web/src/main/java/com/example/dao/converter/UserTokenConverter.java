package com.example.dao.converter;

import com.example.model.UserToken;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class UserTokenConverter extends AbstractConverter<UserToken, String> {

  public UserTokenConverter() {
    super(UserToken::value, UserToken::of);
  }
}
