package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;

import java.util.List;

public interface TestTubeService {

    List<TestTubeItemDto> findAll();

    List<TestTubeResultDto> create(Long orderResultId, List<Long> testTubeItemIdList);

    List<TestTubeResultNurseDto> findByOrderId(Long orderId);

    void updateStatus(long orderId, int statusId);
}
