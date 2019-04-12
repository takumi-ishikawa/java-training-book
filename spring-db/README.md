Spring + Database の練習
---

準備
---

* postgresql の docker image(`postgres:11``)
  * docker を用意できない場合は [db-fiddle](https://db-fiddle.com) を使います

概要
---

この演習ではデータベースを取り扱う練習をします。
基本的な `SELECT`/`UPDATE`/`INSERT`/`DELETE` 文の他、 `CREATE TABLE` などを実行します。

---

本題準備
---

ユーザーが保尊している alias を管理するシステムのデータベースを考えます。
このシステムで保持しているテーブルは以下のとおりです。

![ERD](http://www.plantuml.com/plantuml/png/ZP2n2i8m48RtF4L6HJ9KKQ5JNLnS77GgKixsq62IId8fYF7TfPNQ8hGuviznt_zTuWWiUQqO6fBqu4U7rl4dutp6lKERoI8-ouiqnDFzxiI44CsV0Orn1JQ_WfqiLjCcp2q2OP41nIGrEW9zPo_MttqeRsX60Ecs1w5w-4k9vkk_C1ibmM7BWFerPF5Je8OFsrMWV9SkesYy3VkSCWGXGkZKUt4PsguiGLDuhTu0)

これらのテーブルを作るためのSQLは次のようになっています。

```postgresql
create table users (
    user_id bigint primary key not null ,
    name varchar(36) unique not null ,
    created_at timestamp not null 
);

create table user_tokens (
    user_id bigint primary key not null ,
    token varchar(127) not null ,
    created_at timestamp not null ,
    constraint fk_user_tokens_users foreign key (user_id) references users(user_id)
);

create table aliases (
    alias_id bigint primary key not null ,
    user_id bigint not null,
    name varchar(36) not null ,
    value varchar(511) not null ,
    created_at timestamp not null ,
    constraint fk_aliases_users foreign key (user_id) references users(user_id),
    constraint unique_user_id_name unique (user_id, name)
);
```

### 課題1

postgres:11 のイメージを起動して、上記のSQLを流してテーブルを作成してください。

#### 起動

```bash
docker run --rm -d \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_USER=app-user \
  -e POSTGRES_DB=app \
  -p 5432:5432 \
  postgres:11
```

#### コンテナに入る

```bash
docker exec -it `docker ps -f ancestor=postgres:11 -q` /bin/bash
```

#### postgres に接続

```bash
psql --username app-user --dbname app
```

#### SQL 実行

* DDL をコピペして流す
* `\dt` で作成されたテーブルがあるか確認する
* 終わったら、 `\q` を入力して終了

#### docker コンテナからログアウトする


### 課題2

下記のレコードを登録する SQL を流してデータを登録してください

```postgresql
begin transaction ;

insert into users(user_id, name, created_at) values
  (1000, 'John', '2019-01-03 04:05:06.700'),
  (2000, 'Denny', '2019-01-04 05:06:07.800'),
  (3000, 'Paul', '2019-01-05 06:07:08.900'),
  (4000, 'Avril', '2019-01-06 07:08:09.000')
;

insert into user_tokens(user_id, token, created_at) values 
  (1000, '838128c4-acb1-46ba-a3ee-4c995cb0f57c', '2019-01-03 04:05:06.700'),
  (2000, '5902e8df-a472-4bac-b164-782a0185a6ba', '2019-01-04 05:06:07.800'),
  (3000, 'f45e0620-4932-497c-b2dd-108effebb0f1', '2019-01-05 06:07:08.900'),
  (4000, 'e2452225-c753-497d-81a0-4460d0ed5c70', '2019-01-06 07:08:09.000')
;

insert into aliases(alias_id, user_id, name, value, created_at) VALUES 
  (1010, 1000, 'vim', 'emacs', '2019-01-04 08:09:00.100'),
  (1020, 1000, 'emacs', 'vim', '2019-01-05 09:00:01.200'),
  (1030, 1000, 'll', 'ls -la', '2019-01-06 00:01:02.300'),
  (3010, 3000, 'll', 'ls -la', '2019-01-07 01:02:03.400'),
  (4010, 4000, 'll', 'ls -la', '2019-01-08 02:03:04.500'),
  (4020, 4000, 'd', 'docker run', '2019-01-09 03:04:05.600'),
  (4030, 4000, 'log', 'echo', '2019-01-10 04:05:06.700'),
  (4040, 4000, 'vir', 'virtualenv', '2019-01-11 05:06:07.800'),
  (4050, 4000, 'vim', 'emacs', '2019-01-11 05:06:07.800')
;

commit ;
```

SQL
---

SQL には主に DML(Data Manipulation Language) と DDL(Data Definition Language) と DCL(Data Control Language) があります。

* DML ではデータの操作、レコード作成/レコード参照/レコード更新/レコード削除を行います。先程の課題2の内容が DML にあたります。
* DDL ではデータの定義を行います。先程の課題1の内容が DDL にあたります。
* DCL はデータ操作の制御を行うコマンドで、課題2の最初と最後にある `begin transaction`/`commit` などにあたります。


ここでは主に、 DML を扱っていきます。


### SELECT

データを取り出す系列のSQLです文法は次のとおりです(厳密ではない)

```postgresql
SELECT /* 列名 ((as) 別名), 列名 (as) 別名 */
FROM /* テーブル名 ((as) 別名), テーブル名 as 別名 */
WHERE /* テーブル名.カラム名 = 値 AND テーブル名.カラム名2 = 値2 */
;
```

なお、列名の代わりに `*` を使うとすべてのカラムを取得します

#### 課題3

`users` テーブルから全データを取得するSQL を実行してください。

次のようなレコードが出力されます

user_id | name | created_at
-- | -- | --
1000 | John | 2019-01-03 04:05:06.700000
2000 | Denny | 2019-01-04 05:06:07.800000
3000 | Paul | 2019-01-05 06:07:08.900000
4000 | Avril | 2019-01-06 07:08:09.000000

#### 課題4

`users` テーブルから、 `created_at` が `2019-01-05 00:00:00.000` 以前のレコードの `name` を取得するSQLを実行してください


name|
--|
John|
Denny|

#### 課題5

`users` テーブルから `created_at` が `2019-01-05 00:00:00.000` 以前のレコードの `user_id`,`name` を取得するSQLを実行してください。
なお、 `user_id` は `id` という名前で、 `name` は `user_name` という名前で取得できるようにしてください

#### 課題6

`users` テーブルと `user_tokens` テーブルのデータを結合して、 `users.user_id` と `users.name` と `user_tokens.token` を取得するSQLを実行してください。

##### ヒント

* 複数のテーブルを結合する場合は `FROM` の中で `JOIN` 句を使います。その際の結合条件は `join` の後の `on` にて指定できます
  * `users join user_tokens on users.user_id = user_tokens.user_id`

#### 課題7

`aliases` テーブルから `name` 列が `'ll'` であるもののみを取得するSQLを実行してください

##### ヒント

* 条件は `WHERE` 句に記述します。
  * 等しいことをテストするクエリーは `aliases.name = 'll'` の形になります

#### 課題8

`aliases` テーブルから `name` が 文字 `l` から始まっている行のみを取得するSQLを実行してください。

##### ヒント

* 前方一致検索(〜で始まっているものを指定する)するための条件には `LIKE` 演算子を使います。
  * この場合だと `name like 'l%'` となります
    * ここの `%` はなんでもよいという意味になります

#### 課題9

`aliases` テーブルと `users` テーブルから、 `aliases` の `name` が `'vim'` で、名前が `Johanness` であるユーザーを取得するSQLを実行してください。


