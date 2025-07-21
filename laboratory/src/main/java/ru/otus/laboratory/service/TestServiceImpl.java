package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.mapper.TestMapper;
import ru.otus.laboratory.model.TestItem;
import ru.otus.laboratory.model.TestResult;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;

    private final TestRepository testRepository;

    @Override
    public List<TestItemDto> findAll() {
        return testRepository.findAll().stream().map(testMapper::fromModel).toList();
    }

    @Transactional
    @Override
    public List<TestResultDto> create(Long orderId, Long staffId, List<TestItem> testItemList) {
        List<TestResult> testResultList = new ArrayList<>();

        for (TestItem testItem : testItemList) {
            TestResult testResult = new TestResult();
            testResult.setStatusId(TestStatus.CREATE);
            testResult.setTestItemId(testItem.getId());
            testResult.setOrderResultId(orderId);
            testResult.setPrice(testItem.getPrice());
            testResult.setStaffId(staffId);
            testResultList.add(testResult);

            testRepository.createResult(testResult);
        }

        return testResultList.stream().map(testMapper::fromModel).toList();
    }

    @Override
    public List<TestItem> findByIds(List<Long> idList) {
        return testRepository.findByIds(idList);
    }
}
