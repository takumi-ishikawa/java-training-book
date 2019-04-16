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

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OptUser {

  @NotNull
  private final String teamName;
  @NotNull
  private final Opt opt;

  public OptUser(@NotNull String teamName, @NotNull Opt opt) {
    this.teamName = teamName;
    this.opt = opt;
  }

  @NotNull
  String responsiblePerson(@NotNull String position) {
    // opt の responsibleFor から値を取り出す
    // 存在しない場合は opt の getName から取り出した値を返す
    Optional<String> optional = opt.responsibleFor(position);
    return optional.orElse(opt.getName());
  }

  @NotNull
  String teamNameFromOpt() {
    // opt から teamName を取り出す。
    // 値がない場合はこのインスタンスが保持している teamName を返す
    Optional<String> optional = opt.teamName();
    return optional.orElse(teamName);
  }

  @NotNull
  String name() throws IllegalStateException {
    // opt から name を取り出す。
    // 値がない場合は IllegalStateException を投げる
    Optional<String> optional = opt.name();
    return optional.orElseThrow(() -> new IllegalArgumentException("exception"));
  }
}
