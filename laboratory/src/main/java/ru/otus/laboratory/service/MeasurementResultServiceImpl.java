package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.MeasurementItemDto;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.adapter.AdapterTaskResultDto;
import ru.otus.laboratory.mapper.MeasurementMapper;
import ru.otus.laboratory.model.MeasurementResult;
import ru.otus.laboratory.repository.MeasurementResultRepository;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MeasurementResultServiceImpl implements MeasurementResultService {

    private final MeasurementResultRepository measurementResultRepository;

    private final MeasurementItemService measurementItemService;

    private final MeasurementMapper measurementMapper;

    private final DateTimeUtil dateTimeUtil;

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

    @Transactional
    @Override
    public void update(List<Long> testResultIdList, List<AdapterTaskResultDto> adapterTaskResultList) {
        var measurementResultList = measurementResultRepository.findByTestResultIdList(testResultIdList);
        var extcodeResultMap = converToMap(adapterTaskResultList);

        for (MeasurementResult measurementResult : measurementResultList) {
            var measurementItem = measurementItemService.findById(measurementResult.getMeasurementItemId());
            AdapterTaskResultDto taskResult = extcodeResultMap.get(measurementItem.getExtcode());

            if (taskResult == null) {
                continue;
            }

            measurementResult.setMeasurTime(dateTimeUtil.now());
            measurementResult.setValue(taskResult.getValue());
            measurementResultRepository.update(measurementResult);
        }
    }

    private Map<String, AdapterTaskResultDto> converToMap(List<AdapterTaskResultDto> adapterTaskResultDtos) {
        return adapterTaskResultDtos.stream().
                collect(Collectors.toMap(
                        AdapterTaskResultDto::getExtcode,
                        dto -> dto));
    }
}
