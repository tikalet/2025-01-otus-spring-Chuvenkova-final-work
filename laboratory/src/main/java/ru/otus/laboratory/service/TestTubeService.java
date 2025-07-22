package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;

import java.util.List;

public interface TestTubeService {

    List<TestTubeItemDto> findAll();

    List<TestTubeResultDto> create(List<Long> testTubeItemIdList);
}
