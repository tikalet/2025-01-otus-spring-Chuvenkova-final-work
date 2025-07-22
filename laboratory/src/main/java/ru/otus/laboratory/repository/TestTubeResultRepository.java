package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.TestTubeResult;

import java.util.List;

@Mapper
public interface TestTubeResultRepository {

    void create(TestTubeResult testTubeResult);

    List<TestTubeResult> findByOrderId(Long orderId);

    void updateStatus(@Param("orderId") long orderId, @Param("statusId") int statusId);
}
