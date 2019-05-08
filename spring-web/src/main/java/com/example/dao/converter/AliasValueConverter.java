package com.example.dao.converter;

import com.example.model.AliasValue;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class AliasValueConverter extends AbstractConverter<AliasValue, String> {

  public AliasValueConverter() {
    super(AliasValue::value, AliasValue::of);
  }
}
