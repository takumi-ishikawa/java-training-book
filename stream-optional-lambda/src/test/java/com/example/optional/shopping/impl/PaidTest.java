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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

class PaidTest {

    @Test
    void payedAt() {
        YearMonth yearMonth = YearMonth.of(2019, Month.APRIL);
        Instant instant = yearMonth.atDay(2)
                .atTime(OffsetTime.of(11, 2, 3, 400_000, ZoneOffset.UTC))
                .toInstant();
        Paid paid = new Paid(new Purchased(BigDecimal.valueOf(100L), instant), instant.plusSeconds(60 * 60 * 24L));

        assertThat(paid.payedAt(yearMonth)).isTrue();
    }
}
