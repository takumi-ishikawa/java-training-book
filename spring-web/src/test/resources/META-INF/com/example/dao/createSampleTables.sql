create table if not exists "users" (
    "user_id" bigint primary key not null ,
    "name" varchar(36) unique not null ,
    "created_at" timestamp not null
);

create table if not exists "user_tokens" (
    "user_id" bigint primary key not null ,
    "token" varchar(127) not null ,
    "created_at" timestamp not null ,
    constraint fk_user_tokens_users foreign key ("user_id") references "users"("user_id")
);

create table if not exists "aliases" (
    "alias_id" bigint primary key not null ,
    "user_id" bigint not null,
    "name" varchar(36) not null ,
    "value" varchar(511) not null ,
    "created_at" timestamp not null ,
    constraint fk_aliases_users foreign key ("user_id") references "users"("user_id"),
    constraint unique_user_id_name unique ("user_id", "name")
);