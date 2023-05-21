
create sequence if not exists restaurant_id_seq;

alter sequence restaurant_id_seq
    owner to postgres;

create table if not exists restaurant (
    id  bigserial
        constraint restaurant_pk
        primary key,
    name varchar(50)
        not null,
    rating double precision
        not null
);

alter table restaurant
    owner to postgres;

alter sequence restaurant_id_seq
    owned by restaurant.id;


create sequence if not exists post_id_seq
    as integer;

alter sequence post_id_seq
    owner to postgres;

create table if not exists post (
    id serial
        constraint post_pk
        primary key,
    name varchar(50)
        not null
);

alter table post
    owner to postgres;

alter sequence post_id_seq
    owned by post.id;


create sequence if not exists worker_id_seq;

alter sequence worker_id_seq
    owner to postgres;

create table if not exists worker (
                        id bigserial
                            constraint worker_pk
                                primary key,
                        name varchar(50)
                            not null,
                        post_id integer
                            not null
                            constraint worker_post_id_fk
                                references post,
                        restaurant_id bigint
                            not null
                            constraint worker_restaurant_id_fk
                                references restaurant,
                        employment_date date
);

alter table worker
    owner to postgres;

alter sequence worker_id_seq
    owned by worker.id;


create sequence if not exists food_id_seq;

alter sequence food_id_seq
    owner to postgres;

create table if not exists food (
    id  serial
        constraint food_pk
        primary key,
    name varchar(50)
        not null
);

alter table food
    owner to postgres;

alter sequence food_id_seq
    owned by food.id;



create sequence if not exists order_id_seq;

alter sequence order_id_seq
    owner to postgres;

create table if not exists "order" (
    id bigserial
        constraint order_pk
        primary key,
    type integer
        not null
        constraint order_food_id_fk
        references food,
    comment  varchar(200),
    order_time timestamp
        not null,
    is_finished boolean
        default false
        not null,
    restaurant_id bigint
        not null
        constraint order_restaurant_id_fk
        references restaurant
);

alter table "order"
    owner to postgres;

alter sequence order_id_seq
    owned by "order".id;
