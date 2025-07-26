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

    @Cacheable(cacheNames = "orderStatus", key = "#id", condition = "#id != null")
    @Override
    public OrderStatus findOrderStatusById(Integer id) {
        return dictRepository.findOrderStatusById(id);
    }

    @Cacheable(cacheNames = "testStatus", key = "#id", condition = "#id != null")
    @Override
    public TestStatus findTestStatusById(Integer id) {
        return dictRepository.findTestStatusById(id);
    }

    @Cacheable(cacheNames = "testTubeStatus", key = "#id", condition = "#id != null")
    @Override
    public TestTubeStatus findTestTubeStatusById(Integer id) {
        return dictRepository.findTestTubeStatusById(id);
    }

    @Cacheable(cacheNames = "testTubeError", key = "#id", condition = "#id != null")
    @Override
    public TestTubeError findTestTubeErrorById(Integer id) {
        return testTubeErrorRepository.findById(id);
    }
}
