package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.OrderStatus;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.model.TestTubeStatus;

@Mapper
public interface DictRepository {

    OrderStatus findOrderStatusById(Integer id);

    TestStatus findTestStatusById(Integer id);

    TestTubeStatus findTestTubeStatusById(Integer id);

}
