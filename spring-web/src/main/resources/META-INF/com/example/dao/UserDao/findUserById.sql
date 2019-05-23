select "u"."user_id" as "user_id",
       "u"."name" as "name",
       "ut"."token" as "token",
       "u"."created_at" as "created_at"
from "users" "u" join "user_tokens" "ut" on "u"."user_id" = "ut"."user_id"
where "u"."user_id" = /* userId */1000
;
