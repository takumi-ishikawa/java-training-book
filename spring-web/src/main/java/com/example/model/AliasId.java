package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AliasId {

    private final long value;

    @Contract(pure = true)
    private AliasId(final long value) {
        this.value = value;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static AliasId of(final long value) {
        return new AliasId(value);
    }

    public long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliasId aliasId = (AliasId) o;
        return value == aliasId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AliasId{" +
                "value=" + value +
                '}';
    }
}
