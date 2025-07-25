package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.TestMapper;
import ru.otus.laboratory.repository.TestItemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestItemServiceImpl implements TestItemService {

    private final TestMapper testMapper;

    private final TestItemRepository testItemRepository;

    @Override
    public List<TestItemDto> findAll() {
        return testItemRepository.findAll().stream().map(testMapper::fromModel).toList();
    }

    @Cacheable(cacheNames = "testItem", key = "#id")
    @Override
    public TestItemDto findById(long id) {
        return testMapper.fromModel(testItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test item with id %d not found".formatted(id))));
    }
}
