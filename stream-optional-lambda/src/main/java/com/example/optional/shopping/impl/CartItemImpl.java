/*
 * Copyright 2019 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.optional.shopping.impl;

import com.example.optional.shopping.CartItem;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public class CartItemImpl implements CartItem {

    private final int id;
    @NotNull
    private final String name;
    @NotNull
    private final BigDecimal price;
    private final int quantity;
    @NotNull
    private final Instant added;

    public CartItemImpl(
            int id,
            @NotNull String name,
            @NotNull BigDecimal price,
            int quantity,
            @NotNull Instant added) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.added = added;
    }

    @Override
    public int itemId() {
        return id;
    }

    @NotNull
    @Override
    public String itemName() {
        return name;
    }

    @NotNull
    @Override
    public BigDecimal price() {
        return price;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @NotNull
    @Override
    public Instant added() {
        return added;
    }
}
