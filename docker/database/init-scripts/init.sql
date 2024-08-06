DROP TABLE IF EXISTS translation_requests;

create table if not exists translation_requests
(
    id serial
        primary key,
    source_text text not null,
    translated_text text not null,
    target_lang varchar(5) not null,
    source_lang varchar(5) not null,
    created_at timestamp with time zone default CURRENT_TIMESTAMP,
    ip_address varchar(30)
);

alter table translation_requests owner to postgres;

