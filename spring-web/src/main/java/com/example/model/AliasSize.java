package com.example.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AliasSize {

    private final long value;

    @Contract(pure = true)
    private AliasSize(final long value) {
        this.value = value;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static AliasSize of(final long value) {
        return new AliasSize(value);
    }

    public long value() {
        return value;
    }

    public AliasSize increment() {
        return AliasSize.of(value + 1);
    }

    public AliasSize decrement() {
        return AliasSize.of(value - 1);
    }

    public void validate() {
        if (value < 0) {
            throw new IllegalArgumentException("Invalid AliasSize. Negative values can not be used.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliasSize that = (AliasSize) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AliasSize{" +
                "value=" + value +
                '}';
    }
}
