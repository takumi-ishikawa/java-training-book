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
import com.example.optional.shopping.Payment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class Paid implements Payment, Bill {

    @NotNull
    private final Purchased purchased;
    @NotNull
    private final Instant paymentDate;

    @Contract(pure = true)
    public Paid(@NotNull Purchased purchased, @NotNull Instant paymentDate) {
        this.purchased = purchased;
        this.paymentDate = paymentDate;
    }

    @NotNull
    @Override
    public Optional<Payment> payment() {
        return Optional.of(this);
    }

    @Override
    public @NotNull BigDecimal total() {
        return purchased.total();
    }

    @Override
    public @NotNull Instant purchased() {
        return purchased.purchased();
    }

    @Override
    public @NotNull Optional<Instant> date() {
        return Optional.of(paymentDate);
    }
}
