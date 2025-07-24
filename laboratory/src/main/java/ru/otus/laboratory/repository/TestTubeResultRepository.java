package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.TestTubeResult;
import ru.otus.laboratory.model.search.TestTubeResultSearch;

import java.util.List;

@Mapper
public interface TestTubeResultRepository {

    void create(TestTubeResult testTubeResult);

    List<TestTubeResult> findByParam(@Param("search") TestTubeResultSearch testTubeResultSearch,
                                     @Param("searchCondition") String searchCondition);

    void updateStatusByOrder(@Param("orderId") long orderId, @Param("statusId") int statusId);

    void updateStatus(@Param("id") long id, @Param("statusId") int statusId);
}
