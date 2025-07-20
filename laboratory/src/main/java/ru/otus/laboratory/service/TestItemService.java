package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestItemDto;

import java.util.List;

public interface TestItemService {

    List<TestItemDto> findAll();
}
