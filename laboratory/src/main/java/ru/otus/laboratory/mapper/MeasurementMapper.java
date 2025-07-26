package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.MeasurementItemDto;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.model.MeasurementItem;
import ru.otus.laboratory.model.MeasurementResult;

@Component
public class MeasurementMapper {

    public MeasurementItemDto fromModel(MeasurementItem measurementItem) {
        MeasurementItemDto measurementItemDto = new MeasurementItemDto();
        measurementItemDto.setId(measurementItem.getId());
        measurementItemDto.setExtcode(measurementItem.getExtcode());
        measurementItemDto.setMax(measurementItem.getMax());
        measurementItemDto.setUnit(measurementItem.getUnit());
        measurementItemDto.setName(measurementItem.getName());
        measurementItemDto.setMin(measurementItem.getMin());
        return measurementItemDto;
    }

    public MeasurementResultDto fromModel(MeasurementResult measurementResult,
                                          MeasurementItemDto measurementItemDto,
                                          String analyzer) {
        MeasurementResultDto measurementResultDto = new MeasurementResultDto();
        measurementResultDto.setId(measurementResult.getId());
        measurementResultDto.setMeasurementItem(measurementItemDto);
        measurementResultDto.setMeasurTime(measurementResult.getMeasurTime());
        measurementResultDto.setTestResultId(measurementResult.getTestResultId());
        measurementResultDto.setAnalyzer(analyzer);
        measurementResultDto.setValue(measurementResult.getValue());
        return measurementResultDto;
    }
}
