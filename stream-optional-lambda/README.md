Stream と Optional と ラムダ
===

ラムダ式
---

### 概要

* ラムダ式は **1つの実装すべきメソッドだけを持つインターフェース** に対してシンプルに実装を記述する方法で、
後に紹介する `Optional` や `Stream` で一緒に使うために開発されました。
**1つの実装すべきメソッドだけを持つインターフェース** のことを `FunctionalInterface` とよび、
そのことをコンパイラーに伝えるアノテーションとして `@FunctionalInterface` が提供されています。

#### 練習1

次の中から `FunctionalInterface` であるものをすべて選び、コンパイラーを用いて確認してください。

```java
interface Foo {}

interface Bar {
  void doSomething(String string);
}

interface Baz extends Bar {
  int countSomething(String string);
}

interface Qux extends Bar {
  @Override
  default void doSomething(String string) {
    for(char ch: string.toCharArray()) {
      doEachChar(ch);
    }
  }

  void doEachChar(char ch);
}
```

### ラムダ式

ラムダ式の書き方は以下のとおりです。

```
(TypeName1 arg1, TypeName2 arg2, TypeName3 arg3) -> { /* do something */ };
```

例えば、同じ型のもの同士を比較する `Comparator<T>` は次のように書けます。

```jshelllanguage
Comparator<User> orderByUserId = (User left, User right) -> {
    return Integer.compare(left.userId, right.userId);
};
```

#### 引数の省略

`Comparator<User>` の定義は次のとおりであり、ラムダのパラメーター部分の型はあらかじめわかっています。

```jshelllanguage
interface Comparator<T> {
  int compare(T o1, T o2);
}
```

このような場合には、ラムダのパラメーター部分の型を省略できます。

```jshelllanguage
Comparator<User> orderByUserId = (left, right) -> {
    return Integer.compare(left.userId, right.userId);
};
```

また、 `Comparator<T>` ではパラメーターは２つあるので、これ以上の省略は無理ですが、
パラメーターが一つであるような関数型インターフェースの場合、さらにパラメーターの周りの丸括弧 `(` 、 `)` 
が省略可能です。

例: `Consumer<T>` - `T` 型のパラメーターを受け取り、 `void`(何も返さない処理を行う) な関数型インターフェース

```jshelllanguage
Consumer<String> showString = string -> { System.out.println(string); };
```

#### body の省略

ラムダの中の処理部分の式が1つである場合には、両端にある波括弧 `{` 、 `}` と、
戻り値を示す `return` を省略できます。

```jshelllanguage
Comparator<User> orderByUserId = (left, right) ->
  Integer.compare(left.userId, right.userId);
```

#### メソッドリファレンス

さらに次のようなパターンの場合、パラメーターと処理部分をさらに省略できます。

1. パラメーターの型がもつ、引数が `0` 個のメソッドを呼び出す場合
1. 参照可能な変数/定数のメソッド あるいは `static` メソッドにパラメーターを順番通りに渡せる

```jshelllanguage
// 1. の例(元は string -> string.toUpperCase())
Function<String, String> toUpper = String::toUpperCase;

// 2. の例(1) (元は instant -> DateTimeFormatter.ISO_INSTANT.format(instant))
Function<Instant, String> formatIso8601 = DateTimeFormatter.ISO_INSTANT::format;

// 2. の例(2) (元は (left,right) -> String.join(left, right))
BiFunction<CharSequence, CharSequence, String> joining = String::join

// 2. の例(3) (元は (left, right) -> left.concat(right))
BiFunction<String, String, String> concat = String::concat
```

#### 練習2

次の処理から、ラムダにできるものをすべてラムダにしてみてください

```java
class Main {
  public static void main(String[] args) throws Exception {
    ClassLoader classLoader = Main.class.getClassLoader();
    URL url = classLoader.getResource("test-result.txt");
    if (url == null) {
      throw new IllegalStateException("file not found");
    }
    CountDownLatch latch = new CountDownLatch(1);
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    try(BufferedReader reader = new BufferedReader(
            new InputStreamReader(classLoader.getResourceAsStream("test-result.txt")))) {
      executorService.submit(new Runnable() {
        @Override
        public void run() {
          try {
            while(reader.ready()) {
              String line = reader.readLine();
              System.out.println(line);
            }
          } catch (IOException e) {
            e.printStackTrace();
          } finally{
            latch.countDown();
          }
        }
      });
      latch.await();
    } finally{
      executorService.shutdown();
    }
  }
}
```

---

`Optional`
---

* `Optional` はデータの有無を表すデータ型で、 Java8 から取り入れられたものです。 Java8 以前ではデータの有無を表す
専用の型は存在しておらず、 `Object` が `null` となっているなど、型の状態で値の有無を判別する方法はなく、
誤って値が存在していないのにメソッドを実行してしまい `NullPointerException` を発生させてくるなど、Java (に限らず様々な言語の)
問題点とされてきました(もちろん、そのことを回避した Haskell/Scala などがあります)。

### インスタンス生成(of/ofNullable/empty)

`Optional` の本質は値の有無ですので、値の有無それぞれに該当するインスタンス生成のためのメソッドが提供されています。
なお、 `Optional` はジェネリックな型なので、型パラメーターの指定が必要になりますが、
変数の型またはメソッドの戻り値の型などが与えられている場合は、そこから推論可能ですので、
型パラメーターを省略できます。

```jshelllanguage
// 値がある
var valuePresent = Optional.of("item");
// 値がない(var からは型が推論できないので、明示的に与える必要がある)
var valueNotPresent = Optional.<String>empty();
```

また、値の有無が不明の場合のメソッドも提供されています。

```jshelllanguage
var valueUnknown = Optional.of(someUnknownMethod());
```

#### 練習1

このプロジェクトにある `com.example.optional.Opt` クラスを完成させてください。

### データの取り出し(orElse/orElseGet/orElseThrow)

データの有無が表現できても、中に入っている値が取り出せなければ意味がありません。
そこで、 `Optional` からデータを取り出す方法が提供されています。

```jshelllanguage
Optional<String> optional = someTryableMethod()

// 値がない場合のデフォルト値を提供して値を取り出す
String valueWithDefault = optional.orElse("default-value");

// 値がない場合のデフォルト値の生成メソッドを呼び出す
String valueWithDefaultGet = optional.orElseGet(() -> getDefaultValue());

// 値がない場合は例外
String valueOrException = optional.orElseThrow(() -> new IllegalStateException("exception"));
```

`Optional#orElseGet` および `Optional#orElseThrow` にわたすラムダの型はそれぞれ
`Supplier<? extends T>` 、 `Supplier<? extends Throwable>` です。

#### 練習2

このプロジェクトにある `com.example.optional.OptUser` クラスを完成させてください。

### データ変換1(map)

### データ変換2(flatMap)

### 注意事項

// `Optional#get` はバグなので使わないこと(Java10 で `orElseThrow` を使えというノートがついた)
// `Optional` はメソッドの戻り値の型として設計されている(`Optional` が要求されているものに `null` を使うのはバグ)ため、
//  クラスのフィールド、メソッドパラメーターに使わないこと
// `Optional` がパラメーターにあるメソッドには `if` 文があることが暗示されるので、メソッドが複雑であることが想定できる
// (メソッドを複雑にするなら、単純な２つのメソッドを用意したほうがよいということ)

`Stream`
---

* `Stream` は記述量が多く、簡潔にコレクションの操作を書きづらい or 大量のデータに対して複数のCPUを使って並行に
効果的な処理を記述ができるようになるために Java8 から取り入れられた仕組みで、 `Stream` 自体はデータの列を表します。
ちなみに同じ `Stream` の名前を持つ `InpuStream` 、 `OutputStreamReader` などとは全く関係ありません。

### インスタンス生成

### 終端操作1(forEach)

### 終端操作2(collect)

### 終端操作3(reduce)

### 中間操作1(map)

### 中間操作1(filter)

### 中間操作1(flatMap)
