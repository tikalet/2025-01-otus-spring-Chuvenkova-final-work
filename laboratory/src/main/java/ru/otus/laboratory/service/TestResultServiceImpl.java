package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestResultOrderDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.mapper.TestMapper;
import ru.otus.laboratory.model.TestResult;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.repository.TestResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {

    private final TestMapper testMapper;

    private final TestResultRepository testResultRepository;

    private final TestItemService testItemService;

    private final DictService dictService;

    @Transactional
    @Override
    public List<TestResultOrderDto> create(Long orderId, Long staffId, List<TestItemDto> testItemList,
                                           List<TestTubeResultOrderDto> testTubeResultOrderDtoList) {
        List<TestResultOrderDto> testResultList = new ArrayList<>();

        Map<Long, TestTubeResultOrderDto> testTubeResultDtoMap = convertTestTubeResultToMap(testTubeResultOrderDtoList);

        for (TestItemDto testItem : testItemList) {
            TestTubeResultOrderDto testTubeResultOrderDto = testTubeResultDtoMap.get(testItem.getTestTubeId());

            TestResult testResult = new TestResult();
            testResult.setStatusId(TestStatus.CREATE);
            testResult.setTestItemId(testItem.getId());
            testResult.setOrderResultId(orderId);
            testResult.setPrice(testItem.getPrice());
            testResult.setStaffId(staffId);
            testResult.setTestTubeResultId(testTubeResultOrderDto.getId());

            testResultRepository.create(testResult);

            testResultList.add(testMapper.fromModel(testResult, testItem, testTubeResultOrderDto,
                    dictService.findTestStatusById(testResult.getStatusId()).getName()));
        }

        return testResultList;
    }

    @Transactional
    @Override
    public void updateStatusByOrderId(Long orderId, int statusId) {
        testResultRepository.updateStatusByOrderId(orderId, statusId);
    }

    @Transactional
    @Override
    public void updateStatusByTestTubeResultId(Long testTubeResultId, int statusId) {
        testResultRepository.updateStatusByTestTubeResultId(testTubeResultId, statusId);
    }

    @Override
    public List<TestResultDto> findByTestTubeResultId(Long testTubeResultId) {
        List<TestResult> testResultList = testResultRepository.findByTestTubeResultId(testTubeResultId);

        if (testResultList == null || testResultList.isEmpty()) {
            return new ArrayList<>();
        }

        return testResultList.stream().map(
                testResult -> testMapper.fromModel(testResult,
                        testItemService.findById(testResult.getTestItemId()),
                        dictService.findTestStatusById(testResult.getStatusId()).getName())
        ).toList();
    }

    @Override
    public boolean allTestReady(Long orderId) {
        List<TestResult> testResultList = testResultRepository.findByOrderId(orderId);

        if (testResultList == null || testResultList.isEmpty()) {
            return false;
        }

        boolean allReady = true;
        for (TestResult testResult : testResultList) {
            if (testResult.getStatusId() < TestStatus.READY) {
                allReady = false;
                break;
            }
        }

        return allReady;
    }

    private Map<Long, TestTubeResultOrderDto> convertTestTubeResultToMap(
            List<TestTubeResultOrderDto> testTubeResultOrderDtoList) {
        return testTubeResultOrderDtoList.stream().
                collect(Collectors.toMap(
                        dto -> dto.getTestTubeItem().getId(),
                        dto -> dto));
    }
}
