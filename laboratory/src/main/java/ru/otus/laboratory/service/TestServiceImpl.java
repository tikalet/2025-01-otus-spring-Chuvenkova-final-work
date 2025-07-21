package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.mapper.TestIMapper;
import ru.otus.laboratory.model.TestItem;
import ru.otus.laboratory.repository.TestRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestIMapper testIMapper;

    private final TestRepository testRepository;

    @Override
    public List<TestItemDto> findAll() {
        return testRepository.findAll().stream().map(testIMapper::fromModel).toList();
    }

    @Override
    public List<TestResultDto> create(Long orderId, List<TestItem> testItemList) {
        return List.of();
    }

    @Override
    public List<TestItem> findByIds(List<Long> idList) {
        return testRepository.findByIds(idList);
    }
}
