select a.alias_id as alias_id,
       a.user_id as user_id,
       a.name as name,
       a.value as value,
       a.created_at as created_at
from aliases as a;