package com.example.dao.converter;

import com.example.model.AliasName;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class AliasNameConverter extends AbstractConverter<AliasName, String> {
  public AliasNameConverter() {
    super(AliasName::value, AliasName::new);
  }
}
