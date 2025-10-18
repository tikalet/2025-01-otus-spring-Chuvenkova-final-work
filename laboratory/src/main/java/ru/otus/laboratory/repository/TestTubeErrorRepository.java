package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestTubeError;

import java.util.List;

@Mapper
public interface TestTubeErrorRepository {

    List<TestTubeError> findAll();

    TestTubeError findById(Integer id);
}
