package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AliasValue {

    private final String value;

    @Contract(pure = true)
    private AliasValue(final String value) {
        this.value = value;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static AliasValue of(final String value) {
        return new AliasValue(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliasValue that = (AliasValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AliasValueConverter{" +
                "value='" + value + '\'' +
                '}';
    }
}
