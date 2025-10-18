INSERT INTO patient (id, first_name, mid_name, last_name, birthday, phone, email, search_pattern)
VALUES (1, 'Test', 'Test', 'Test', '1979-01-01', '+7(900)-00-000-00', 'test@test.ru', 'TEST TEST TEST');

INSERT INTO patient (id, first_name, mid_name, last_name, birthday, phone, email, search_pattern)
VALUES (2, 'Test_DELETE', 'Test', 'Test', '1979-01-01', '+7(900)-00-000-00', 'test@test.ru', 'Test_DELETE TEST TEST');

INSERT INTO order_result(id, total_price, status_id, payment_time, staff_id, patient_id)
VALUES(1, 1000, 1, '2025-07-30T15:30:00.000+03:00', 2, 1);

INSERT INTO test_tube_result(id, order_result_id, barcode, test_tube_item_id, status_id, test_tube_error_id, take_test_time, disposal_time)
VALUES(1, 1, '0000000001', 1, 1, null, '2025-07-30T15:30:00.000+03:00', '2025-08-13T15:30:00.000+03:00');

INSERT INTO test_result(id, test_item_id, order_result_id, status_id, staff_id, test_tube_result_id, price)
VALUES(1, 1, 1, 1, 2, 1, 1000);

INSERT INTO measurement_result(id, test_result_id, patient_id, measurement_item_id, value, measur_time)
VALUES(1, 1, 1, 1, 11.1, '2025-07-30T15:30:00.000+03:00');

