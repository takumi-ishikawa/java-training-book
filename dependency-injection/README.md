Dependency Injection
---

Java で大きなアプリケーションを作る際に、どうしても問題になるのが依存するクラスをどのように扱うかです。

問題を発見する
---

このプロジェクトの `UserService` を使ったアプリケーション `UserApp` を作ってみましょう。

* `UserApp` は `UserServiceImpl` に依存していますので、 `UserServiceImpl` のインスタンスを生成しないといけません。
* `UserServiceImpl` は `IdGeneratorImpl` と `MapBasedUserRepository` に依存しています。
* `MapBasedUserRepository` は `MutableMapStore` に依存しています

### 練習問題

* すべてのオブジェクトを作って、  `UserApp` のインスタンスを作り、 `runApp` メソッドを実行してください

---

オブジェクト生成と DI
---

このように具象クラスに依存している超巨大な依存関係性のあるアプリケーションでは

* アプリケーションで利用するコンポーネントの組み立てが困難
* 柔軟に利用するオブジェクトを変更できない
* テストするために依存オブジェクトをすべて組み立てないといけない

といった複雑さに由来する問題が発生します

そこで、インターフェースと DI という技術によって、オブジェクト生成に関する困難さを回避します。

Guice
---

`Guice` では `Module` というオブジェクトに設定した依存関係をもとに、 `Injector` というオブジェクトがオブジェクトを生成します。

まずは、オブジェクトに依存しているクラスに対して、 `Injector` が依存ライブラリーを構築できるようにマーキングします。
マーキングとして、 `@javax.inject.Inject` というアノテーションを利用します。


### 練習問題

先程の `MapBasedUserRepository` クラスに `MutableMapStore` が inject されるように、 `javax.inject` のアノテーションを付与して、実際にインスタンスを取得してください。

##### 手順

1. `MapBasedUserRepository` のコンストラクターに `@Inject`(`javax.inject.Inject`)を付与する
1. テストクラス(名前もパッケージもなんでもよい)を作る
1. テストクラスの中に static な内部クラス `Module` を作り、 `AbstractModule` クラスを継承させる
1. テストクラスのテストメソッド(`@Test` アノテーションがついている)にて、 `Injector` のインスタンスをつくる(`Guice.createInjector(new Module())`)
1. つくった `Injector` から `MapBasedUserRepository` クラスのインスタンスを取得する
1. 取得された `MapBasedUserRepository` が正しいインスタンスであるか確認する

インターフェース
---

次に `TimerApp` の方でインターフェースを使う方法を学びます。

`TimerApp` は `TimeServiceImpl` に依存しています。

### 練習問題

* `TimerApp` に `TimeServiceImpl` を使ってインスタンス化して `TimerApp` の `main` を完成させてください

---

`TimeServiceImpl` は `UTC` で時刻を返すのですが、テストで使う場合などでは時刻を指定できないために、
`TimeServiceImpl` に依存するクラスはテストしづらいです(例えば未来の日付を必要とするケースなど)。

そこで、 `TimeService` をインターフェースにして、好きな時刻を返せるように調整できるようにします。

### 練習問題

* `TimerApp` のフィールドおよびコンストラクターのパラメーターの型、 `TimeServiceImpl` をそのインターフェース(`TimeService`)に変更してください。
* 同様に `UserApp` も具象型 `TimeServiceImpl` に依存しているので、これを `TimeService` に変更してください。
* `UserServiceImpl` のフィールドにも具象クラスがそのまま使われていますので、インターフェースに置き換えてください
  * ヒント : `MapBasedUserRepository` はインターフェース `UserRepository` の実装クラスです

これで、 `TimeService` を実装するクラスなら、なにでも `TimerApp` にて利用できるようになりました。
テストなどで、インターフェースを使ったモッキングをおこないます。ぜひインターフェースの便利さを覚えておきましょう。

---

統合
---

Guice でインターフェースに具象クラスを割り当てる場合は `Module` クラスで指定します。

### 練習問題

`UserApp` と同じパッケージに `AppModule` クラスを作成し、 `AbstractModule` クラスを継承させてください

次にインターフェースと具象クラスの割当を設定します。これは `AbstractModule` に定義されている `configure` メソッドにておこないます。
インターフェースと具象クラスの割当は `bind({ インターフェースクラス }).to({ 実装クラス })` にて行なえます。

### 練習問題

すべてのインターフェースと具象クラスをバインドさせてください。

#### ヒント

* `TimeService` の実装クラスは `TimeServiceImpl` です
* `UserService` の実装クラスは `UserServiceImpl` です
* `IdGenerator` の実装クラスは `IdGeneratorImpl` です
* `UserRepository` の実測ラスは `MapBasedUserRepository` です

### 練習問題

つくったモジュールをつかって、 `UserApp` クラスを動かしてみましょう。
