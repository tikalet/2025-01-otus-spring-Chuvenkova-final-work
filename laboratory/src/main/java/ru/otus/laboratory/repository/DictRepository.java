package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.OrderStatus;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.model.TestTubeStatus;

@Mapper
public interface DictRepository {

    OrderStatus findOrderStatusById(int id);

    TestStatus findTestStatusById(int id);

    TestTubeStatus findTestTubeStatusById(int id);

}
