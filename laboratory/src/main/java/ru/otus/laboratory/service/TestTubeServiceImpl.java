package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.model.TestItem;
import ru.otus.laboratory.repository.TestTubeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestTubeServiceImpl implements TestTubeService {

    private final TestTubeRepository testTubeRepository;

    private final TestTubeMapper testTubeMapper;

    @Override
    public List<TestTubeItemDto> findAll() {
        return testTubeRepository.findAll().stream().map(testTubeMapper::fromModel).toList();
    }

    @Override
    public List<TestTubeResultDto> create(Long orderId, List<TestItem> testItemList, List<TestResultDto> testResultDtoList) {
        return List.of();
    }

}
