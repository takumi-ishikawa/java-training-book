package com.example.dao.converter;

import com.example.model.UserId;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class UserIdConverter extends AbstractConverter<UserId, Long> {

  public UserIdConverter() {
    super(UserId::value, UserId::of);
  }
}
