package com.example.dao.converter;

import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.seasar.doma.jdbc.domain.DomainConverter;

abstract class AbstractConverter<APP, DB> implements DomainConverter<APP, DB> {

  @NotNull private final Function<? super APP, ? extends DB> toDbValue;

  @NotNull private final Function<? super DB, ? extends APP> toAppValue;

  @Contract(pure = true)
  AbstractConverter(
      final @NotNull Function<? super APP, ? extends DB> toDbValue,
      final @NotNull Function<? super DB, ? extends APP> toAppValue) {
    this.toDbValue = toDbValue;
    this.toAppValue = toAppValue;
  }

  @Override
  public DB fromDomainToValue(final APP app) {
    return toDbValue.apply(app);
  }

  @Override
  public APP fromValueToDomain(final DB value) {
    return toAppValue.apply(value);
  }
}
