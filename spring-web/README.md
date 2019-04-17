aliases web system
---

このシステムは 
REST によって、
サーバーのリソースに動詞(HTTPメソッド)によるアクセスによって
alias などのリソースの追加・変更・削除を
ユーザーがおこなうシステムです。

---

#### 例

現在、 ユーザー自身のリソースを `GET` によりアクセスできるようにしてあります。

```http request
GET /users/1000

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 17 Apr 2019 05:12:57 GMT

{"userId":1000,"name":"John","createdAt":"2019-01-03T04:05:06.700Z"}
```

#### 演習

以下のリソースを作ってください

##### 1. ユーザーを作成するリソース

ユーザーを作成します。
結果は成功 or 失敗で返されます。 

* リソース URL - `/users`
* メソッド - `POST`
* パラメーター
  * `token` - ユーザーのトークン
  * `name` - ユーザーの名前
* レスポンス
  * `success` - `bool` 成功 : `true`/失敗 : `false`
  * `message` - `string`  `"success"`

失敗時のメッセージはユーザーがわかるように設定してください

##### 2. ユーザー自身を取得するリソース

ユーザーの情報を取得します

* リソース URL - `/users/{name}` (`name` はユーザー名)
* メソッド - `GET`
* レスポンス
  * `name` - `string` ユーザー名
  * `id` - `int64` ユーザーのID
  * `created_at` - `string` - ユーザーの作成日(ISO8601形式)

ユーザーが存在しない場合は、成功・失敗 + メッセージを返してください

#####3. ユーザーのトークンを更新するリソース

ユーザーのトークンを更新します。
結果は成功 or 失敗で返されます。 

* リソース URL - `/users/{name}` (`name` はユーザー名)
* メソッド - `PUT`
* 必須ヘッダー - `X-USER-TOKEN` - ユーザートークン
* パラメーター
  * `token` - ユーザーの新しいトークン
* レスポンス
  * `success` - `bool` 成功 : `true`/失敗 : `false`
  * `message` - `string`  `"success"`

注意

* ヘッダーのトークンが一致しない場合は 401 を返してください
* ユーザーが見つからない場合は 404 を返してください
* 各エラーの場合は失敗を返してください



