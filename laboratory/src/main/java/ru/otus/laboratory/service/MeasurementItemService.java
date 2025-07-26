package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.MeasurementItemDto;

import java.util.List;

public interface MeasurementItemService {

    List<MeasurementItemDto> findByTestItemId(long testItemId);

    MeasurementItemDto findById(long id);
}
