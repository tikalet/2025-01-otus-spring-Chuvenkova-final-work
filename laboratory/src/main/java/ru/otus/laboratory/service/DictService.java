package ru.otus.laboratory.service;

import ru.otus.laboratory.model.OrderStatus;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeStatus;

public interface DictService {

    OrderStatus findOrderStatusById(Integer id);

    TestStatus findTestStatusById(Integer id);

    TestTubeStatus findTestTubeStatusById(Integer id);

    TestTubeError findTestTubeErrorById(Integer id);
}
