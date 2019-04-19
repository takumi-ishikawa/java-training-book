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
  * `token` - ユーザーのトークン - `[a-zA-Z0-9-]{8,127}`
  * `name` - ユーザーの名前 - `[A-Za-z][A-Za-z0-9_-]{2,35}`
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
  * `user_id` - `int64` ユーザーのID
  * `created_at` - `string` - ユーザーの作成日(ISO8601形式)

ユーザーが存在しない場合は、成功・失敗 + メッセージを返してください

##### 3. ユーザーのトークンを更新するリソース

ユーザーのトークンを更新します。
結果は成功 or 失敗で返されます。 

* リソース URL - `/users/{name}` (`name` はユーザー名)
* メソッド - `PUT`
* 必須ヘッダー - `X-USER-TOKEN` - ユーザートークン
* パラメーター
  * `token` - ユーザーの新しいトークン `{"token":"foo-bar-baz"}`
* レスポンス
  * `success` - `bool` 成功 : `true`/失敗 : `false`
  * `message` - `string`  `"success"`

注意

* ヘッダーのトークンが一致しない場合は 401 を返してください
* ユーザーが見つからない場合は 404 を返してください
* 各エラーの場合は失敗を返してください

##### 4. ロギング

アプリケーションのログの理想は、変更・不具合があった場合に、アプリケーションがどういう状態であったかを取得できるのが望ましいです。
そこで、ここまでの(およびこの後)部分で、以下のケースのログを取得するようにしてください。

* サーバーに変更があった場合
* エラーが発生した場合

なお、ログに出力する内容は

* ログの種類(成功のログかエラーのログか)
* 発生場所を識別する情報
* ユーザーのリクエスト内容
* エラーがあった場合はエラーのメッセージ
* エラーがあった場合はエラーの種類

##### 5. ユーザーを削除するリソース

ユーザーを削除します。
結果は成功 or 失敗で返されます。

* リソース URL - `/users`
* メソッド - `DELETE`
* 必須ヘッダー - `X-USER-TOKEN` - ユーザートークン
* パラメーター - なし
* レスポンスステータス - 成功時 : `204 - NO CONTENT`
* レスポンスボディ
  * `success` - `bool` 成功 : `true`/失敗 : `false`
  * `message` - `string`  `"success"`

##### 6. エイリアスのリストを取得するリソース

エイリアスを取得します。
ページングに対応して、ユーザーが指定した1ページあたりの件数、ページ位置に従ったエイリアスのリストを取得します。

* リソース URL - `/users/{name}/aliases`
* メソッド - `GET`
* 必須ヘッダー - `X-USER-TOKEN` - ユーザートークン
* パラメーター
  * `page` - ページ(のインデックス/デフォルト: `0`)
  * `size` - 1ページあたり件数(デフォルト: `10`)
* レスポンス - 以下のyamlを参考

```yaml
page: 2 # ユーザー指定のページ
next_page: 3 #次のページ(次がない場合は省略)
size: 10 # 1ページあたりのサイズ
contents:
  - alias_id: 123456 # alias の id
    name: "ll" #alias の名前
    value: "ls -la" #alias の値
    url: http://localhost:8080/users/test-user/aliases/ll # alias の URL
  - alias_id: 123456 # alias の id
    name: "ll" #alias の名前
    value: "ls -la" #alias の値
    url: http://localhost:8080/users/test-user/aliases/ll # alias の URL
```

