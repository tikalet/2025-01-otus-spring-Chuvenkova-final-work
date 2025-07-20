package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestItem;

import java.util.List;

@Mapper
public interface TestItemRepository {

    List<TestItem> findAll();
}
