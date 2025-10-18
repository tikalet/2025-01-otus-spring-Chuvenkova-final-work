package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.repository.TestTubeErrorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestTubeErrorServiceImpl implements TestTubeErrorService {

    private final TestTubeErrorRepository testTubeErrorRepository;

    private final TestTubeMapper testTubeMapper;

    @Override
    public List<TestTubeErrorDto> findAll() {
        return testTubeErrorRepository.findAll().stream().map(testTubeMapper::fromModel).toList();
    }
}
