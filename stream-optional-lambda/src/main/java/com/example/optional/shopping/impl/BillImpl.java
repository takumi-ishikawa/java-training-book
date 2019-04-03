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
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class BillImpl implements Bill {

    @Nullable
    private final Instant purchasedDate;
    @NotNull
    private final BigDecimal total;

    @Contract(pure = true)
    private BillImpl(@Nullable Instant purchasedDate, @NotNull BigDecimal total) {
        this.purchasedDate = purchasedDate;
        this.total = total;
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Bill purchased(@NotNull Instant purchasedDate, @NotNull BigDecimal total) {
        return new BillImpl(purchasedDate, total);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Bill notPurchased(@NotNull BigDecimal total) {
        return new BillImpl(null, total);
    }

    @Override
    public Optional<Payment> payment() {
        // purchasedDate は null になる可能性のあるフィールドです
        // この値が null の場合は empty を返します
        // この値がある場合は Purchased のインスタンスを返します
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal total() {
        return total;
    }
}
