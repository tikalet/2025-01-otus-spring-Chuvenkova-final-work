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

CREATE TABLE test_item(
    id bigint,
    name varchar,
    price int
);

ALTER TABLE test_item ADD PRIMARY KEY (id);
CREATE SEQUENCE test_item_seq;
