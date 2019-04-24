 select user_id, token, created_at
from user_tokens
where
  token = /* userToken.value() */'FooBar'
;