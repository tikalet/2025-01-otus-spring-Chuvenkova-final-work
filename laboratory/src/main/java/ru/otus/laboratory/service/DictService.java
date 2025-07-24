package ru.otus.laboratory.service;

import ru.otus.laboratory.model.OrderStatus;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeStatus;

public interface DictService {

    OrderStatus findOrderStatusById(int id);

    TestStatus findTestStatusById(int id);

    TestTubeStatus findTestTubeStatusById(int id);

    TestTubeError findTestTubeErrorById(Integer id);
}
