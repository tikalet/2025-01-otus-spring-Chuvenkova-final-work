CREATE TABLE patient(
    id bigint,
    first_name varchar,
    mid_name varchar,
    last_name varchar,
    birthday varchar,
    phone varchar,
    email varchar,
    search_pattern varchar
);

ALTER TABLE patient ADD PRIMARY KEY (id);

CREATE SEQUENCE patient_seq START WITH 100;
