package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.MeasurementResult;

import java.util.List;

@Mapper
public interface MeasurementResultRepository {

    void create(MeasurementResult measurementResult);

    void update(MeasurementResult measurementResult);

    List<MeasurementResult> findByTestResultId(Long testResultId);

    List<MeasurementResult> findByPatientIdAndMeasurementItemId(@Param("patientId") Long patientId,
                                                                @Param("measurementItemId") Long measurementItemId);
}
