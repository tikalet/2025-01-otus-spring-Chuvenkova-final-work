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
    (nextval('test_item_seq'),'Группа крови и резус-фактор (Blood Type + Rh factor) [система AB0]',450.0, 2),
    (nextval('test_item_seq'),'Глюкоза (Glucose)',145.0, 4),
    (nextval('test_item_seq'),'Билирубин (Bilirubin) фракции',350.0, 2),
    (nextval('test_item_seq'),'Креатинин (Creatinine)',170.0, 2),
    (nextval('test_item_seq'),'Мочевина (Urea)',160.0, 2),
    (nextval('test_item_seq'),'Мочевая кислота (Uric acid)',160.0, 2),
    (nextval('test_item_seq'),'Белок общий (Protein total)',160.0, 2),
    (nextval('test_item_seq'),'Альбумин (Albumin)',160.0, 2),
    (nextval('test_item_seq'),'Триглицериды (Triglycerides)',160.0, 2),
    (nextval('test_item_seq'),'Холестерин общий (Cholesterol total)',160.0, 2),
    (nextval('test_item_seq'),'Аполипопротеины А1 и В (Apolipoproteines А1 & В)',600.0, 2),
    (nextval('test_item_seq'),'Липопротеин (a) (Lipoprotein (a))',650.0, 2),
    (nextval('test_item_seq'),'Активированное частичное тромбопластиновое время (АЧТВ) (APTT)',240.0, 3),
    (nextval('test_item_seq'),'Протромбин (Prothrombin)',260.0, 3),
    (nextval('test_item_seq'),'Тромбиновое время (Thrombin time)',260.0, 3),
    (nextval('test_item_seq'),'Фибриноген (Fibrinogen)',260.0, 3),
    (nextval('test_item_seq'),'Волчаночный антикоагулянт (Lupus anticoagulant)',380.0, 3),
    (nextval('test_item_seq'),'D-димер (D-dimer)',990.0, 3),
    (nextval('test_item_seq'),'Фактор свёртывания крови VIII (Factor VIII)',880.0, 3),
    (nextval('test_item_seq'),'Антитромбин III (Antithrombin III)',410.00, 3)
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
    (5, 'Архив'),
    (6, 'Утилизировано'),
    (7, 'Ошибка')
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

INSERT INTO analyzer(id, name) VALUES (1, 'Sysmex XT-4000i');

