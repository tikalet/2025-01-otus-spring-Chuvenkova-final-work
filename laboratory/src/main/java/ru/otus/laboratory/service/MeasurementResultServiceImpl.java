package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.converter.MeasurementConverter;
import ru.otus.laboratory.dto.MeasurementItemDto;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.adapter.AdapterTaskResultDto;
import ru.otus.laboratory.exceptions.BadSearchParamException;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.MeasurementMapper;
import ru.otus.laboratory.model.MeasurementResult;
import ru.otus.laboratory.model.search.MeasurementResultSearch;
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

    private final MeasurementConverter measurementConverter;

    @Transactional
    @Override
    public List<MeasurementResultDto> create(List<TestResultDto> testResultDtoList, Long patientId) {
        List<MeasurementResultDto> measurementResultDtos = new ArrayList<>();

        for (TestResultDto testResultDto : testResultDtoList) {
            var measurementItemDtoList = measurementItemService.findByTestItemId(testResultDto.getTestItem().getId());

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
    public void update(String barcode, List<AdapterTaskResultDto> adapterTaskResultList) {
        MeasurementResultSearch measurementResultSearch = new MeasurementResultSearch();
        measurementResultSearch.setBarcode(barcode);

        var measurementResultList = findMeasurementResultList(measurementResultSearch);
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

    @Override
    public boolean fillAllResult(String barcode) {
        return measurementResultRepository.fillAllResult(barcode);
    }

    @Override
    public List<MeasurementResultDto> findByTestTubeResultBarcode(String barcode) {
        MeasurementResultSearch measurementResultSearch = new MeasurementResultSearch();
        measurementResultSearch.setBarcode(barcode);

        return prepareMeasurementResultDtoList(measurementResultSearch);
    }

    @Override
    public List<MeasurementResultDto> findByOrderId(Long id) {
        MeasurementResultSearch measurementResultSearch = new MeasurementResultSearch();
        measurementResultSearch.setOrderId(id);

        return prepareMeasurementResultDtoList(measurementResultSearch);
    }

    @Override
    public List<MeasurementResultDto> findByPatientIdAndMeasurementItemId(Long patientId, Long measurementItemId) {
        MeasurementResultSearch measurementResultSearch = new MeasurementResultSearch();
        measurementResultSearch.setPatientId(patientId);
        measurementResultSearch.setMeasurementItemId(measurementItemId);

        List<MeasurementResultDto> measurementResultDtos = prepareMeasurementResultDtoList(measurementResultSearch);

        if (measurementResultDtos == null || measurementResultDtos.isEmpty()) {
            throw new NotFoundException("Not found measurement result by param%s"
                    .formatted(measurementConverter.measurementResultSearchToString(measurementResultSearch)));
        }

        return measurementResultDtos;
    }

    private Map<String, AdapterTaskResultDto> converToMap(List<AdapterTaskResultDto> adapterTaskResultDtos) {
        return adapterTaskResultDtos.stream().
                collect(Collectors.toMap(
                        AdapterTaskResultDto::getExtcode,
                        dto -> dto));
    }

    private List<MeasurementResultDto> prepareMeasurementResultDtoList(MeasurementResultSearch measurementResultSearch) {
        List<MeasurementResult> measurementResultList = findMeasurementResultList(measurementResultSearch);

        return measurementResultList.stream()
                .map(measurementResult -> measurementMapper.fromModel(measurementResult,
                        measurementItemService.findById(measurementResult.getMeasurementItemId()))).toList();
    }

    private List<MeasurementResult> findMeasurementResultList(MeasurementResultSearch measurementResultSearch) {
        String condition = createSearchConditionResult(measurementResultSearch);
        return measurementResultRepository.findByParam(measurementResultSearch, condition);
    }

    private String createSearchConditionResult(MeasurementResultSearch measurementResultSearch) {
        StringBuilder condition = new StringBuilder();

        if (measurementResultSearch.getMeasurementItemId() != null) {
            condition.append(" AND mr.measurement_item_id= #{search.measurementItemId}");
        }

        if (measurementResultSearch.getPatientId() != null) {
            condition.append(" AND mr.patient_id= #{search.patientId}");
            condition.append(" AND mr.value IS NOT NULL");
        }

        if (measurementResultSearch.getOrderId() != null) {
            condition.append(" AND tr.order_result_id= #{search.orderId}");
        }

        if (measurementResultSearch.getBarcode() != null && !measurementResultSearch.getBarcode().isEmpty()) {
            condition.append(" AND ttr.barcode = #{search.barcode}");
        }

        if (condition.isEmpty()) {
            throw new BadSearchParamException("Incorrect measurement result search data");
        }
        return condition.toString();
    }
}
