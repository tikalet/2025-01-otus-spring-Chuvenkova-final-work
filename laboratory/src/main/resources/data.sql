INSERT INTO test_tube_item (id, name)
VALUES
    (nextval('test_tube_item_seq'),'Пробирка 2 мл с фиолетовой крышкой'),
    (nextval('test_tube_item_seq'),'Пробирка 4 мл с красной крышкой'),
    (nextval('test_tube_item_seq'),'Пробирка 3.5 мл с голубой крышкой'),
    (nextval('test_tube_item_seq'),'Пробирка 2 мл с серой крышкой'),
    (nextval('test_tube_item_seq'),'Пробирка 1.5 мл с черной крышкой')
;

INSERT INTO test_item (id, name, price, test_tube_item_id)
VALUES
    (nextval('test_item_seq'),'Общий анализ венозной крови [24 показателя]',320.0, 1),
    (nextval('test_item_seq'),'Скорость оседания эритроцитов (СОЭ) (ESR) венозной крови',170.0, 5),
    (nextval('test_item_seq'),'Глюкоза (Glucose)',145.0, 4),
    (nextval('test_item_seq'),'Билирубин (Bilirubin) фракции',350.0, 2),
    (nextval('test_item_seq'),'Холестерин общий (Cholesterol total)',160.0, 2),
    (nextval('test_item_seq'),'Активированное частичное тромбопластиновое время (АЧТВ) (APTT)',240.0, 3),
    (nextval('test_item_seq'),'Протромбин (Prothrombin)',260.0, 3)
;
-------------------------------
INSERT INTO order_status
VALUES
    (1, 'Создан'),
    (2, 'В работе'),
    (3, 'Завершен'),
    (4, 'Отменен');

INSERT INTO test_status
VALUES
    (1, 'Создан'),
    (2, 'В работе'),
    (3, 'Готово'),
    (4, 'Отменен');

 -------------------------------
INSERT INTO test_tube_error(id, error_text, need_notify_patient)
VALUES
    (nextval('test_tube_error_seq'), 'Тара разбита', 1),
    (nextval('test_tube_error_seq'), 'Биоматериал испорчен', 1),
    (nextval('test_tube_error_seq'), 'Биоматериала недостаточно', 1),
    (nextval('test_tube_error_seq'), 'Реагент закончился', 0),
    (nextval('test_tube_error_seq'), 'Реагент просрочен', 0),
    (nextval('test_tube_error_seq'), 'Результаты некорректные', 0)
;

INSERT INTO test_tube_status
VALUES
    (1, 'Подразделение'),
    (2, 'Транспортировка'),
    (3, 'В лаборатории'),
    (4, 'В работе'),
    (5, 'Отработан'),
    (6, 'Архив'),
    (7, 'Утилизировано'),
    (8, 'Ошибка')
;

INSERT INTO staff_position(id, name)
VALUES
    (1, 'Консультант'),
    (2, 'Лаборант'),
    (3, 'Врач КЛД'),
    (4, 'Главный врач лаборатории'),
    (5, 'Медсестра');

INSERT INTO staff (id, first_name, mid_name, last_name, position_id)
VALUES
    (nextval('staff_seq'), 'Аркадий', 'Петрович', 'Жариков', 2),
    (nextval('staff_seq'), 'Алла', 'Николаевна', 'Сорокина', 1),
    (nextval('staff_seq'), 'Борис', 'Давидович', 'Пирогов', 4),
    (nextval('staff_seq'), 'Савелий', 'Николаевич', 'Гоголь', 3),
    (nextval('staff_seq'), 'Ирина', 'Петровна', 'Буравчик', 5)
;

INSERT INTO measurement_item (id, name, unit, min, max)
VALUES
(nextval('measurement_item_seq'), 'Гемоглобин (HGB)','грамм/литр',112.70,159.10),
(nextval('measurement_item_seq'), 'Гематокрит (HCT)','%',35.54,53.54),
(nextval('measurement_item_seq'), 'Эритроциты (RBC)','10^12/литр',3.69,5.55),
(nextval('measurement_item_seq'), 'Средний объем эритроцитов (MCV)','фемтолитр',78.33,109.73),
(nextval('measurement_item_seq'), 'Среднее содержание гемоглобина в эритроците (MCH)','пикограмм',25.33,36.08),
(nextval('measurement_item_seq'), 'Средняя концентрация гемоглобина в эритроците (MCHC)','грамм/дл',27.89,34.89),
(nextval('measurement_item_seq'), 'Ширина распределения эритроцитов по объёму (RDW-SD)','фемтолитр',35.00,46.00),
(nextval('measurement_item_seq'), 'Ширина распределения эритроцитов по объёму (RDW-CV)','%',11.00,16.00),
(nextval('measurement_item_seq'), 'Тромбоциты (PLT)','10^9/литр',150.00,400.00),
(nextval('measurement_item_seq'), 'Тромбокрит (PCT)','%',0.17,0.35),
(nextval('measurement_item_seq'), 'Средний объем тромбоцита (MPV)','фемтолитр',8.00,12.00),
(nextval('measurement_item_seq'), 'Ширина распределения тромбоцитов (PDW)','фемтолитр',9.00,17.00),
(nextval('measurement_item_seq'), 'Содержание крупных тромбоцитов (P-LCR)','%',15.00,35.00),
(nextval('measurement_item_seq'), 'Лейкоциты (WBC)','10^9/литр',5.34,17.06),
(nextval('measurement_item_seq'), 'Нейтрофилы (NEUT#)','10^9/литр',1.50,7.00),
(nextval('measurement_item_seq'), 'Нейтрофилы (NEUT%)','%',36.00,58.10),
(nextval('measurement_item_seq'), 'Эозинофилы (EO#)','10^9/литр',0.02,0.62),
(nextval('measurement_item_seq'), 'Эозинофилы (EO%)','%',1.00,5.80),
(nextval('measurement_item_seq'), 'Базофилы (BASO#)','10^9/литр',0.00,0.12),
(nextval('measurement_item_seq'), 'Базофилы (BASO%)','%',0.00,1.20),
(nextval('measurement_item_seq'), 'Лимфоциты (LYMPH#)','10^9/литр',1.00,3.70),
(nextval('measurement_item_seq'), 'Лимфоциты (LYMPH%)','%',29.10,51.00),
(nextval('measurement_item_seq'), 'Моноциты (MONO#)','10^9/литр',0.05,0.80),
(nextval('measurement_item_seq'), 'Моноциты (MONO%)','%',3.50,11.17);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 1, generate_series(1, 24);

--
INSERT INTO measurement_item (id, name, unit, min, max)
VALUES (nextval('measurement_item_seq'), 'Скорость оседания эритроцитов (СОЭ) (ESR) венозной крови','мм/час',0.00,15.50);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 2, 25;

--
INSERT INTO measurement_item (id, name, unit, min, max)
VALUES (nextval('measurement_item_seq'), 'Глюкоза (Glucose)','ммоль/литр',3.53,5.39);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 3, 26;

--
INSERT INTO measurement_item (id, name, unit, min, max)
VALUES
(nextval('measurement_item_seq'), 'Билирубин общий (Bilirubin total)','мкмоль/литр',2.00,150.00),
(nextval('measurement_item_seq'), 'Билирубин прямой/связанный (Direct bilirubin)','мкмоль/литр',0.00,5.10),
(nextval('measurement_item_seq'), 'Билирубин непрямой/свободный (Indirect bilirubin)','мкмоль/литр',0.00,13.70);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 4, generate_series(27, 29);

--
INSERT INTO measurement_item (id, name, unit, min, max)
VALUES (nextval('measurement_item_seq'), 'Холестерин общий (Cholesterol total)','ммоль/литр',1.90,5.17);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 5, 30;

--
INSERT INTO measurement_item (id, name, unit, min, max)
VALUES (nextval('measurement_item_seq'), 'Активированное частичное тромбопластиновое время (АЧТВ) (APTT)','секунда',17.30,35.20);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 6, 31;

--
INSERT INTO measurement_item (id, name, unit, min, max)
VALUES (nextval('measurement_item_seq'), 'Протромбин % по Квику (Prothrombin)','%',74.40,120.00);

INSERT INTO test_item_measurement_item_link(test_item_id, measurement_item_id)
SELECT 7, 32;

UPDATE measurement_item
SET extcode = 'M' || id;

-------------------------
insert into staff_auth (staff_id, login, password)
values
       (1, 'labass', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u'), -- лаборант
       (2, 'cons', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u'), -- консультант
       (3, 'headdoc', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u'), -- глав врач
       (4, 'doc', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u'), -- доктор
       (5, 'nurse', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u'); -- медсестра

insert into authorities(authority)
values ('LABORATORY'), -- 1
       ('PATIENT'), -- 2
       ('NURSE'), -- 3
       ('DOCTOR'), -- 4
       ('LAB_ASSISTANT'), -- 5
       ('HEAD_DOCTOR'); -- 6

insert into staff_authority_link(staff_id, authority_id)
select id, 1
from staff;

insert into staff_authority_link(staff_id, authority_id)
values
       (1, 5),
       (3, 6),
       (3, 4),
       (4, 4),
       (5, 3);
