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
import com.example.optional.shopping.ShoppingCart;
import com.example.optional.shopping.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserImpl implements User {

    @Nullable
    private final ShoppingCart cart;
    @NotNull
    private final List<Bill> bills;

    @Contract(pure = true)
    public UserImpl(@Nullable ShoppingCart cart, @NotNull List<Bill> bills) {
        this.cart = cart;
        this.bills = bills;
    }

    @NotNull
    @Override
    public ShoppingCart shoppingCart() {
        if (cart == null) {
            return new FilledCart(Collections.emptyList());
        }
        return cart;
    }

    @NotNull
    @Override
    public Optional<Bill> bill(YearMonth yearMonth) {
        return bills.stream()
                .filter(bill -> bill.payedAt(yearMonth))
                .findFirst();
    }

    @NotNull
    @Override
    public List<Bill> allPayments() {
        return bills;
    }
}
