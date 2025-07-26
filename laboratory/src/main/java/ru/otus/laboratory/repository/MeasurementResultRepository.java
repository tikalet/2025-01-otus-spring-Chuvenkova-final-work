package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.MeasurementResult;

@Mapper
public interface MeasurementResultRepository {

    void create(MeasurementResult measurementResult);
}
