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

-------------------------------
CREATE TABLE test_item(
    id bigint,
    name varchar,
    price int,
    test_tube_item_id bigint
);

ALTER TABLE test_item ADD PRIMARY KEY (id);
CREATE SEQUENCE test_item_seq;

-------------------------------
CREATE TABLE test_tube_item(
    id bigint,
    name varchar
);

ALTER TABLE test_tube_item ADD PRIMARY KEY (id);
CREATE SEQUENCE test_tube_item_seq;

-------------------------------
CREATE TABLE staff(
    id bigint,
    first_name varchar,
    mid_name varchar,
    last_name varchar,
    position_id bigint
);

ALTER TABLE staff ADD PRIMARY KEY (id);
CREATE SEQUENCE staff_seq;

CREATE TABLE staff_position(
    id bigint,
    name varchar
);

ALTER TABLE staff_position ADD PRIMARY KEY (id);
CREATE SEQUENCE staff_position_seq;

ALTER TABLE staff ADD CONSTRAINT staff_position_fk FOREIGN KEY (position_id) REFERENCES staff_position (id);

-------------------------------
CREATE TABLE order_status(
    id int,
    name varchar
);

ALTER TABLE order_status ADD PRIMARY KEY (id);

CREATE TABLE order_result(
    id bigint,
    total_price integer,
    status_id int,
    payment_time varchar,
    staff_id bigint,
    patient_id bigint
);

ALTER TABLE order_result ADD PRIMARY KEY (id);
CREATE SEQUENCE order_result_seq;
ALTER TABLE order_result ADD CONSTRAINT order_result_to_status_fk FOREIGN KEY (status_id) REFERENCES order_status (id);
ALTER TABLE order_result ADD CONSTRAINT order_result_to_staff_fk FOREIGN KEY (staff_id) REFERENCES staff (id);
ALTER TABLE order_result ADD CONSTRAINT order_result_to_patient_fk FOREIGN KEY (patient_id) REFERENCES patient (id);


-------------------------------
CREATE TABLE test_tube_error(
    id int,
    error_text varchar,
    need_notify_patient SMALLINT DEFAULT 0
);

ALTER TABLE test_tube_error ADD PRIMARY KEY (id);
CREATE SEQUENCE test_tube_error_seq INCREMENT BY 10;

    CREATE TABLE test_tube_status(
    id int,
    name varchar
);

ALTER TABLE test_tube_status ADD PRIMARY KEY (id);

CREATE TABLE test_tube_result(
    id bigint,
    barcode varchar,
    test_tube_item_id bigint,
    status_id int,
    test_tube_error_id int
);

ALTER TABLE test_tube_result ADD PRIMARY KEY (id);
CREATE SEQUENCE test_tube_result_seq;
ALTER TABLE test_tube_result ADD CONSTRAINT test_tube_result_to_item_fk FOREIGN KEY (test_tube_item_id) REFERENCES test_tube_item (id);
ALTER TABLE test_tube_result ADD CONSTRAINT test_tube_result_to_status_fk FOREIGN KEY (status_id) REFERENCES test_tube_status (id);
ALTER TABLE test_tube_result ADD CONSTRAINT test_tube_result_to_error_fk FOREIGN KEY (test_tube_error_id) REFERENCES test_tube_error (id);

CREATE TABLE test_tube_track(
    id bigint,
    test_tube_result_id bigint,
    staff_id bigint,
    changed_time varchar,
    status_id_old int,
    status_id_new int,
    test_tube_error_id int
);

ALTER TABLE test_tube_track ADD PRIMARY KEY (id);
CREATE SEQUENCE test_tube_track_seq;
ALTER TABLE test_tube_track ADD CONSTRAINT test_tube_track_to_result_fk FOREIGN KEY (test_tube_result_id) REFERENCES test_tube_result (id);
ALTER TABLE test_tube_track ADD CONSTRAINT test_tube_track_to_staff_fk FOREIGN KEY (staff_id) REFERENCES staff (id);
ALTER TABLE test_tube_track ADD CONSTRAINT test_tube_track_to_status_old_fk FOREIGN KEY (status_id_old) REFERENCES test_tube_status (id);
ALTER TABLE test_tube_track ADD CONSTRAINT test_tube_track_to_status_new_fk FOREIGN KEY (status_id_new) REFERENCES test_tube_status (id);
ALTER TABLE test_tube_track ADD CONSTRAINT test_tube_track_to_error_fk FOREIGN KEY (test_tube_error_id) REFERENCES test_tube_error (id);
-------------------------------
CREATE TABLE test_status(
    id int,
    name varchar
);

ALTER TABLE test_status ADD PRIMARY KEY (id);


CREATE TABLE test_result(
    id bigint,
    test_item_id bigint,
    order_result_id bigint,
    status_id int,
    staff_id bigint,
    test_tube_result_id bigint
);
ALTER TABLE test_result ADD COLUMN price int;
ALTER TABLE test_result ADD PRIMARY KEY (id);
CREATE SEQUENCE test_result_seq;
ALTER TABLE test_result ADD CONSTRAINT test_result_to_status_fk FOREIGN KEY (status_id) REFERENCES test_status (id);
ALTER TABLE test_result ADD CONSTRAINT test_result_to_order_result_fk FOREIGN KEY (order_result_id) REFERENCES order_result(id);
ALTER TABLE test_result ADD CONSTRAINT test_result_to_staff_fk FOREIGN KEY (staff_id) REFERENCES staff (id);
ALTER TABLE test_result ADD CONSTRAINT test_result_to_item_fk FOREIGN KEY (test_item_id) REFERENCES test_item(id);
ALTER TABLE test_result ADD CONSTRAINT test_result_to_tube_result_fk FOREIGN KEY (test_tube_result_id) REFERENCES test_tube_result(id);

-------------------------------
CREATE SEQUENCE barcode_seq;
-------------------------------