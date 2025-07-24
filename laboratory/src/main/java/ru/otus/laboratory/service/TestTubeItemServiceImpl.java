package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.repository.TestTubeItemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestTubeItemServiceImpl implements TestTubeItemService {

    private final TestTubeItemRepository testTubeItemRepository;

    private final TestTubeMapper testTubeMapper;


    @Override
    public List<TestTubeItemDto> findAll() {
        return testTubeItemRepository.findAll().stream().map(testTubeMapper::fromModel).toList();
    }

    @Cacheable(cacheNames = "testTubeItem", key = "#id")
    @Override
    public TestTubeItemDto findById(long id) {
        return testTubeMapper.fromModel(testTubeItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test tube item with id %d not found".formatted(id))));
    }


}
