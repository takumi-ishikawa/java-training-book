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
package com.example.optional.shopping;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.function.Predicate;

public interface Bill {

    @NotNull
    Optional<Payment> payment();

    @NotNull
    default Optional<Instant> paymentDate() {
        return payment().flatMap(Payment::date);
    }

    default boolean payedAt(@NotNull YearMonth yearMonth) {
        Instant startOfMonth = yearMonth.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endOfMonth = yearMonth.atEndOfMonth().plusDays(1L).atStartOfDay(ZoneOffset.UTC).toInstant();

        Predicate<Instant> beforeStartOfMonth = instant -> instant.isBefore(startOfMonth);
        Predicate<Instant> beforeEndOfMonth = endOfMonth::isAfter;

        Predicate<Instant> withInYearMonth = beforeStartOfMonth.negate().and(beforeEndOfMonth);

        return paymentDate().filter(withInYearMonth).isPresent();
    }

    @NotNull
    BigDecimal total();
}
