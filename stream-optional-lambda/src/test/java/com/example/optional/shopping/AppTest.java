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

import com.example.optional.shopping.impl.CartItemImpl;
import com.example.optional.shopping.impl.FilledCart;
import com.example.optional.shopping.impl.SessionImpl;
import com.example.optional.shopping.impl.UserImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    private static Instant date(int year, int month, int day, int hour, int min) {
        return LocalDateTime.of(year, month, day, hour, min)
                .atOffset(ZoneOffset.UTC)
                .toInstant();
    }

    private final Session session = new SessionImpl(
            Map.of(1, new UserImpl(null, Collections.emptyList()),
                    3, new UserImpl(new FilledCart(
                            List.of(new CartItemImpl(100, "お手拭き", BigDecimal.valueOf(200L), 2, date(2019, 2, 3, 4, 5)))
                    ), Collections.emptyList())
            ));

    @Test
    void findUsersCartItemBecomesEmpty() {
        App app = new App(session);
        Optional<List<CartItem>> usersCartItem = app.findUsersCartItem(1);

        assertThat(usersCartItem).isEmpty();
    }

    @Test
    void findUsersCartItemNotEmpty() {
        App app = new App(session);
        Optional<List<CartItem>> usersCartItem = app.findUsersCartItem(3);

        assertThat(usersCartItem).isPresent();
    }
}
