package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.mapper.TestMapper;
import ru.otus.laboratory.model.TestItem;
import ru.otus.laboratory.model.TestResult;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.repository.TestItemRepository;
import ru.otus.laboratory.repository.TestResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;

    private final TestItemRepository testItemRepository;

    private final TestResultRepository testResultRepository;

    private final DictService dictService;

    @Override
    public List<TestItemDto> findAll() {
        return testItemRepository.findAll().stream().map(testMapper::fromModel).toList();
    }

    @Transactional
    @Override
    public List<TestResultDto> create(Long orderId, Long staffId, List<TestItem> testItemList,
                                      List<TestTubeResultOrderDto> testTubeResultOrderDtoList) {
        List<TestResultDto> testResultList = new ArrayList<>();

        Map<Long, TestTubeResultOrderDto> testTubeResultDtoMap = convertTestTubeResultToMap(testTubeResultOrderDtoList);

        for (TestItem testItem : testItemList) {
            TestTubeResultOrderDto testTubeResultOrderDto = testTubeResultDtoMap.get(testItem.getTestTubeId());

            TestResult testResult = new TestResult();
            testResult.setStatusId(TestStatus.CREATE);
            testResult.setTestItemId(testItem.getId());
            testResult.setOrderResultId(orderId);
            testResult.setPrice(testItem.getPrice());
            testResult.setStaffId(staffId);
            testResult.setTestTubeResultId(testTubeResultOrderDto.getId());

            testResultRepository.create(testResult);

            TestItemDto testItemDto = testMapper.fromModel(testItem);
            testResultList.add(testMapper.fromModel(testResult, testItemDto, testTubeResultOrderDto,
                    dictService.findTestStatusById(testResult.getStatusId()).getName()));
        }

        return testResultList;
    }

    @Transactional
    @Override
    public void updateStatus(long orderId, int statusId) {
        testResultRepository.updateStatus(orderId, statusId);
    }

    @Override
    public List<TestItem> findByIds(List<Long> idList) {
        return testItemRepository.findByIds(idList);
    }

    private Map<Long, TestTubeResultOrderDto> convertTestTubeResultToMap(List<TestTubeResultOrderDto> testTubeResultOrderDtoList) {
        return testTubeResultOrderDtoList.stream().
                collect(Collectors.toMap(
                        dto -> dto.getTestTubeItem().getId(),
                        dto -> dto));
    }
}
