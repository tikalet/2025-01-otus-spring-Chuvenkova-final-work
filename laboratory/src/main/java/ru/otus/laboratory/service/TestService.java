package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.model.TestItem;

import java.util.List;

public interface TestService {

    List<TestItemDto> findAll();

    List<TestResultDto> create(Long orderId, List<TestItem> testItemList);

    List<TestItem> findByIds(List<Long> idList);
}
