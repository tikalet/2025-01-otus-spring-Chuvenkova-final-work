package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestItem;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TestItemRepository {

    List<TestItem> findAll();

    Optional<TestItem> findById(long id);

}
