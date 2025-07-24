package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.model.OrderStatus;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.repository.DictRepository;
import ru.otus.laboratory.repository.TestTubeErrorRepository;

@RequiredArgsConstructor
@Service
public class DictServiceImpl implements DictService {

    private final DictRepository dictRepository;

    private final TestTubeErrorRepository testTubeErrorRepository;

    @Cacheable(cacheNames = "orderStatus", key = "#id")
    @Override
    public OrderStatus findOrderStatusById(int id) {
        return dictRepository.findOrderStatusById(id);
    }

    @Cacheable(cacheNames = "testStatus", key = "#id")
    @Override
    public TestStatus findTestStatusById(int id) {
        return dictRepository.findTestStatusById(id);
    }

    @Cacheable(cacheNames = "testTubeStatus", key = "#id")
    @Override
    public TestTubeStatus findTestTubeStatusById(int id) {
        return dictRepository.findTestTubeStatusById(id);
    }

    @Cacheable(cacheNames = "testTubeError", key = "#id", condition = "#id != null")
    @Override
    public TestTubeError findTestTubeErrorById(Integer id) {
        return testTubeErrorRepository.findById(id);
    }
}
