package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AliasPage {

    private final long value;

    @Contract(pure = true)
    private AliasPage(final long value) {
        this.value = value;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static AliasPage of(final long value) {
        return new AliasPage(value);
    }

    public long value() {
        return value;
    }

    public void validate() {
        if (value < 0) {
            throw new IllegalArgumentException("Invalid AliasPage, too large");
        }
    }

    public AliasOffset toOffset(AliasSize size) {
        return new  AliasOffset(value * size.value());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliasPage that = (AliasPage) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AliasPage{" +
                "value=" + value +
                '}';
    }
}
