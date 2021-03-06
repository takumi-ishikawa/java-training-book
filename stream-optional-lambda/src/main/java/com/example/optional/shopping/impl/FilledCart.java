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

import com.example.optional.shopping.Bill;
import com.example.optional.shopping.CartItem;
import com.example.optional.shopping.ShoppingCart;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class FilledCart implements ShoppingCart {

    @NotNull
    private final List<CartItem> items;

    @Contract(pure = true)
    public FilledCart(@NotNull List<CartItem> items) {
        this.items = items;
    }

    @NotNull
    @Override
    public List<CartItem> items() {
        return items;
    }

    @NotNull
    @Override
    public Optional<Bill> bill() {
        // TODO Stream の演算によさそう
        throw new UnsupportedOperationException();
    }
}
