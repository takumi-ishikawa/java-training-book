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

import com.example.optional.shopping.impl.BillImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class BillTest {

    @Test
    void purchasedTest() {
        Bill bill = BillImpl.purchased(Instant.now(), BigDecimal.valueOf(2300L));

        assertThat(bill.payment()).isPresent();
    }

    @Test
    void notPurchasedTest() {
        Bill bill = BillImpl.notPurchased(BigDecimal.valueOf(200L));

        assertThat(bill.payment()).isEmpty();
    }
}
