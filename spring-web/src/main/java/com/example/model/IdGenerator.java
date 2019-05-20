package com.example.model;

import java.util.function.LongFunction;
import org.jetbrains.annotations.NotNull;

public interface IdGenerator {

  long createId();

  @NotNull
  default <T> T createId(@NotNull LongFunction<? extends T> constructor) {
    return constructor.apply(createId());
  }
}
