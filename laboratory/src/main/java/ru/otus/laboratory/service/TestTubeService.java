package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.model.TestItem;

import java.util.List;

public interface TestTubeService {

    List<TestTubeItemDto> findAll();

    List<TestTubeResultDto> create(Long orderId, List<TestItem> testItemList, List<TestResultDto> testResultDtoList);
}
