create table users
(
    uuid       uuid         not null primary key,
    email      varchar(320) not null unique,
    first_name varchar(64),
    last_name  varchar(64),
    password   varchar(64)  not null
);
