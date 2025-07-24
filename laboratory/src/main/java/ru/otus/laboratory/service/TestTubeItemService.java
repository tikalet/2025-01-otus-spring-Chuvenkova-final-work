package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestTubeItemDto;

import java.util.List;

public interface TestTubeItemService {

    List<TestTubeItemDto> findAll();

    TestTubeItemDto findById(long id);
}
