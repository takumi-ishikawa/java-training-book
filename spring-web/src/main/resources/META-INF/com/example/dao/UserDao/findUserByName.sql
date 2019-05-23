select "u"."name" as "name",
       "u"."user_id" as "user_id",
       "u"."created_at" as "created_at",
       "ut"."token" as "token"
from "users" "u" join "user_tokens" "ut" on "u"."user_id" = "ut"."user_id"
where "u"."name" = /* userName */'Jhon'
;
