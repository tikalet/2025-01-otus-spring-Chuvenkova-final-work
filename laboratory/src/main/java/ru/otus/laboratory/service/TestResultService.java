package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestResultOrderDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;

import java.util.List;

public interface TestResultService {

    List<TestResultOrderDto> create(Long orderId, Long staffId, List<TestItemDto> testItemList,
                                    List<TestTubeResultOrderDto> testTubeResultOrderDtoList);

    void updateStatusByOrderId(Long orderId, int statusId);

    void updateStatusByTestTubeResultId(Long testTubeResultId, int statusId);

    List<TestResultDto> findByTestTubeResultId(Long testTubeResultId);

    boolean allTestReady(Long orderId);

}
