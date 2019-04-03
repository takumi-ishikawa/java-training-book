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
package com.example.optional;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OptUserTest {

  @TestFactory
  Iterable<DynamicTest> responsiblePersonTest() {
    Opt opt = new Opt("今川義元");
    OptUser optUser = new OptUser("今川軍", opt);

    return Arrays.asList(
        DynamicTest.dynamicTest("奥羽本隊 は map から取得される",
            () -> assertThat(optUser.responsiblePerson("奥羽本隊")).isEqualTo("上杉景勝")),
        DynamicTest.dynamicTest("今川軍本隊は opt の名前から取得",
            () -> assertThat(optUser.responsiblePerson("今川軍本隊")).isEqualTo("今川義元")));
  }

  @Test
  void teamNameTest() {
    Opt opt = new Opt("今川義元");
    OptUser optUser = new OptUser("今川軍", opt);

    assertThat(optUser.teamNameFromOpt()).isEqualTo("今川軍");
  }

  @Test
  void nameTest() {
    Opt opt = new Opt("今川義元");
    OptUser optUser = new OptUser("今川軍", opt);

    assertThat(optUser.name()).isEqualTo("今川義元");
  }
}
