package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestResult;

@Mapper
public interface TestResultRepository {

    void create(TestResult testResult);
}
