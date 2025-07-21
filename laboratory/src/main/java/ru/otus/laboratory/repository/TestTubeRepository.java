package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestTubeItem;

import java.util.List;

@Mapper
public interface TestTubeRepository {
    List<TestTubeItem> findAll();
}
