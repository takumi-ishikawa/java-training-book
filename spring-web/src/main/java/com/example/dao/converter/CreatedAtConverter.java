package com.example.dao.converter;

import com.example.model.CreatedAt;
import java.time.LocalDateTime;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class CreatedAtConverter extends AbstractConverter<CreatedAt, LocalDateTime> {

  public CreatedAtConverter() {
    super(CreatedAt::asLocalDateTime, CreatedAt::of);
  }
}
