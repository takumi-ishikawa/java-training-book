package com.example.dao.converter;

import com.example.model.UserName;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class UserNameConverter extends AbstractConverter<UserName, String> {

  public UserNameConverter() {
    super(UserName::value, value -> {
      try {
        return UserName.of(value);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    });
  }
}
