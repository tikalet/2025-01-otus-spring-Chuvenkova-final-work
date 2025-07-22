package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.model.TestTubeItem;
import ru.otus.laboratory.model.TestTubeResult;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.repository.TestTubeItemRepository;
import ru.otus.laboratory.repository.TestTubeResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestTubeServiceImpl implements TestTubeService {

    private final TestTubeItemRepository testTubeItemRepository;

    private final TestTubeResultRepository testTubeResultRepository;

    private final TestTubeMapper testTubeMapper;

    private final BarcodeService barcodeService;


    @Override
    public List<TestTubeItemDto> findAll() {
        return testTubeItemRepository.findAll().stream().map(testTubeMapper::fromModel).toList();
    }

    @Transactional
    @Override
    public List<TestTubeResultDto> create(Long orderResultId, List<Long> testTubeItemIdList) {
        List<TestTubeResultDto> testTubeResultDtoList = new ArrayList<>();

        List<TestTubeItem> testTubeItemList = testTubeItemRepository.findByIds(testTubeItemIdList);

        for (TestTubeItem tubeItem : testTubeItemList) {
            TestTubeResult testTubeResult = new TestTubeResult();
            testTubeResult.setTestTubeItemId(tubeItem.getId());
            testTubeResult.setStatusId(TestTubeStatus.DIVISION);
            testTubeResult.setBarcode(barcodeService.generateBarcode());
            testTubeResult.setOrderResultId(orderResultId);

            testTubeResultRepository.create(testTubeResult);

            TestTubeItemDto testTubeItemDto = testTubeMapper.fromModel(tubeItem);
            TestTubeErrorDto testTubeErrorDto = new TestTubeErrorDto();
            TestTubeResultDto testTubeResultDto = testTubeMapper.fromModel(testTubeResult, testTubeItemDto,
                    testTubeErrorDto);
            testTubeResultDtoList.add(testTubeResultDto);
        }

        return testTubeResultDtoList;
    }

    @Override
    public List<TestTubeResultNurseDto> findByOrderId(Long orderId) {
        List<TestTubeResult> testTubeResultList = testTubeResultRepository.findByOrderId(orderId);
        List<TestTubeItem> testTubeItemList = testTubeItemRepository.findByIds(convertToItemIdList(testTubeResultList));
        Map<Long, String> testTubeItemMap = convertItemNameToMap(testTubeItemList);

        List<TestTubeResultNurseDto> testTubeResultNurseDtoList = new ArrayList<>();

        for (TestTubeResult testTubeResult : testTubeResultList) {
            testTubeResultNurseDtoList.add(testTubeMapper.fromModel(testTubeResult,
                    testTubeItemMap.get(testTubeResult.getTestTubeItemId())));
        }

        return testTubeResultNurseDtoList;
    }

    @Transactional
    @Override
    public void updateStatus(long orderId, int statusId) {
        testTubeResultRepository.updateStatus(orderId, statusId);
    }

    private List<Long> convertToItemIdList(List<TestTubeResult> testTubeResultList) {
        return testTubeResultList.stream().map(TestTubeResult::getTestTubeItemId).collect(Collectors.toList());
    }

    private Map<Long, String> convertItemNameToMap(List<TestTubeItem> testTubeItemList) {
        return testTubeItemList.stream().collect(Collectors.toMap(
                TestTubeItem::getId,
                TestTubeItem::getName
        ));
    }

}
