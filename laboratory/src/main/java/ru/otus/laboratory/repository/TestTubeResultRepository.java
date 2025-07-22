package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestTubeResult;

@Mapper
public interface TestTubeResultRepository {

    void create(TestTubeResult testTubeResult);
}
