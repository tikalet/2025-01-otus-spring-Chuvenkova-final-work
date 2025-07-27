package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.adapter.AdapterTaskResultDto;

import java.util.List;

public interface MeasurementResultService {

    List<MeasurementResultDto> create(List<TestResultDto> testResultDtoList, Long patientId);

    void update(List<Long> testResultIdList, List<AdapterTaskResultDto> adapterTaskResultList);
}
