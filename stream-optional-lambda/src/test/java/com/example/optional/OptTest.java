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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptTest {

  @Test
  void nameTest() {
    Opt opt = new Opt("test-name");

    Optional<String> name = opt.name();

    assertThat(name)
        .isNotEmpty()
        .hasValue("test-name");
  }

  @Test
  void teamNameTest() {
    Opt opt = new Opt("foo-name");

    Optional<String> teamName = opt.teamName();

    assertThat(teamName).isEmpty();
  }

  @TestFactory
  Iterable<DynamicTest> responsibleForTest() {
    ArrayList<DynamicTest> dynamicTests = new ArrayList<>();

    Opt opt = new Opt("test-name");
    opt.map.entrySet()
        .stream()
        .map(entry -> DynamicTest.dynamicTest(
            String.format("%s is responsible for %s", entry.getValue(), entry.getKey()),
            () -> assertThat(opt.responsibleFor(entry.getKey())).isPresent().hasValue(entry.getValue())))
        .forEach(dynamicTests::add);

    dynamicTests.add(
        DynamicTest.dynamicTest(
            "桶狭間の戦い - 駿府城居留守 は存在しない",
            () -> assertThat(opt.responsibleFor("駿府城居留守")).isEmpty()));
    dynamicTests.add(
        DynamicTest.dynamicTest(
            "桶狭間の戦い - 丸根砦攻撃隊 は存在しない",
            () -> assertThat(opt.responsibleFor("丸根砦攻撃隊")).isEmpty()));
    dynamicTests.add(
        DynamicTest.dynamicTest(
            "桶狭間の戦い - 鷲津砦攻撃隊 は存在しない",
            () -> assertThat(opt.responsibleFor("鷲津砦攻撃隊")).isEmpty()));

    return Collections.unmodifiableList(dynamicTests);
    }
}
