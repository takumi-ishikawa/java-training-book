Spring + Database の練習
---

準備
---

* postgresql の docker image(`postgres:11``)

概要
---

この演習ではデータベースを取り扱う練習をします。
基本的な `SELECT`/`UPDATE`/`INSERT`/`DELETE` 文の他、 `CREATE TABLE` などを実行します。

---

本題
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
  (4020, 4000, 'd', 'docker run', '2019-01-09 03:04:05.600')
;

commit ;
```





