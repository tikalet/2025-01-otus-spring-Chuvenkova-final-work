package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.MeasurementResult;
import ru.otus.laboratory.model.search.MeasurementResultSearch;

import java.util.List;

@Mapper
public interface MeasurementResultRepository {

    void create(MeasurementResult measurementResult);

    void update(MeasurementResult measurementResult);

    List<MeasurementResult> findByParam(@Param("search") MeasurementResultSearch measurementResultSearch,
                                        @Param("searchCondition") String searchCondition);

    boolean fillAllResult(String barcode);
}
