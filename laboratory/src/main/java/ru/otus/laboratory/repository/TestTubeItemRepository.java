package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestTubeItem;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TestTubeItemRepository {
    List<TestTubeItem> findAll();

    Optional<TestTubeItem> findById(long idList);
}
