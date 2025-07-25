package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.TestResult;

import java.util.List;

@Mapper
public interface TestResultRepository {

    void create(TestResult testResult);

    void updateStatus(@Param("orderId") long orderId, @Param("statusId") int statusId);

    List<TestResult> findByTestTubeResultId(Long testTubeResultId);
}
