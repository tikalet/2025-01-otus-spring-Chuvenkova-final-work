package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;

import java.util.List;

public interface TestTubeService {

    List<TestTubeItemDto> findAll();

    List<TestTubeResultOrderDto> create(Long orderResultId, List<Long> testTubeItemIdList);

    List<TestTubeResultNurseDto> findByOrderId(Long orderId);

    void updateStatus(long orderId, int statusId);

    TestTubeResultDto findById(Long id);

    void recordArrivalTestTubeAtLaboratory(String barcode);
}
