package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.MeasurementItemDto;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.MeasurementMapper;
import ru.otus.laboratory.repository.MeasurementItemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeasurementItemServiceImpl implements MeasurementItemService {

    private final MeasurementItemRepository measurementItemRepository;

    private final MeasurementMapper measurementMapper;

    @Override
    public List<MeasurementItemDto> findByTestItemId(long testItemId) {
        return measurementItemRepository.findByTestItemId(testItemId).stream()
                .map(measurementMapper::fromModel)
                .toList();
    }

    @Cacheable(cacheNames = "measurItem", key = "#id")
    @Override
    public MeasurementItemDto findById(long id) {
        return measurementMapper.fromModel(measurementItemRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Measurement item with id %d not found".formatted(id))));
    }
}
