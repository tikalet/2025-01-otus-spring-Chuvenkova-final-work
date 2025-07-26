package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.MeasurementItemDto;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.mapper.MeasurementMapper;
import ru.otus.laboratory.model.MeasurementResult;
import ru.otus.laboratory.repository.MeasurementResultRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeasurementResultServiceImpl implements MeasurementResultService {

    private final MeasurementResultRepository measurementResultRepository;

    private final MeasurementItemService measurementItemService;

    private final MeasurementMapper measurementMapper;

    @Transactional
    @Override
    public List<MeasurementResultDto> create(List<TestResultDto> testResultDtoList, Long patientId) {
        List<MeasurementResultDto> measurementResultDtos = new ArrayList<>();

        for (TestResultDto testResultDto : testResultDtoList) {
            var measurementItemDtoList = measurementItemService.findByTestItemId(testResultDto.getId());

            for (MeasurementItemDto measurementItemDto : measurementItemDtoList) {
                MeasurementResult measurementResult = new MeasurementResult();
                measurementResult.setMeasurementItemId(measurementItemDto.getId());
                measurementResult.setTestResultId(testResultDto.getId());
                measurementResult.setPatientId(patientId);
                measurementResultRepository.create(measurementResult);
                measurementResultDtos.add(measurementMapper.fromModel(measurementResult, measurementItemDto));
            }
        }

        return measurementResultDtos;
    }
}
