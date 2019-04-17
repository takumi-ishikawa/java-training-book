select a.user_id as user_id,
       a.name as name,
       a.value as value
from aliases as a
where user_id = /* userId */3000;