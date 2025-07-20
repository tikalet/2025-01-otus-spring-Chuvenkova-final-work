package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.mapper.TestItemMapper;
import ru.otus.laboratory.repository.TestItemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestItemServiceImpl implements TestItemService {

    private final TestItemMapper testItemMapper;

    private final TestItemRepository testItemRepository;

    @Override
    public List<TestItemDto> findAll() {
        return testItemRepository.findAll().stream().map(testItemMapper::fromModel).toList();
    }
}
