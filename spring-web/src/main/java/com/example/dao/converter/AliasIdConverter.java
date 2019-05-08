package com.example.dao.converter;

import com.example.model.AliasId;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class AliasIdConverter extends AbstractConverter<AliasId, Long> {

  public AliasIdConverter() {
    super(AliasId::value, AliasId::of);
  }
}
