package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.model.TestItem;

import java.util.List;

public interface TestService {

    List<TestItemDto> findAll();

    List<TestItem> findByIds(List<Long> idList);


    List<TestResultDto> create(Long orderId, Long staffId, List<TestItem> testItemList,
                               List<TestTubeResultDto> testTubeResultDtoList);

    void updateStatus(long orderId, int statusId);
    
}
