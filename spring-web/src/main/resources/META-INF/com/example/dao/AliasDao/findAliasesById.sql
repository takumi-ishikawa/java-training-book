select alias_id,
       user_id,
       name,
       value,
       created_at
from aliases
where user_id = /* userId.value() */1000
order by alias_id limit /* aliasSize.value() */5 offset /* aliasOffset.value() */1
;
