package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.MeasurementItem;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MeasurementItemRepository {

    List<MeasurementItem> findByTestItemId(long testItemId);

    Optional<MeasurementItem> findById(long id);
}
