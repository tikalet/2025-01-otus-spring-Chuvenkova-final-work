package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.converter.TestTubeConverter;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.exceptions.BadSearchParamException;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.model.TestTubeItem;
import ru.otus.laboratory.model.TestTubeResult;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.model.search.TestTubeResultSearch;
import ru.otus.laboratory.repository.TestTubeItemRepository;
import ru.otus.laboratory.repository.TestTubeResultRepository;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestTubeServiceImpl implements TestTubeService {

    private static final int DEFAULT_DISPOSAL_DAY = 7;

    private final TestTubeItemRepository testTubeItemRepository;

    private final TestTubeResultRepository testTubeResultRepository;

    private final TestTubeMapper testTubeMapper;

    private final BarcodeService barcodeService;

    private final DateTimeUtil dateTimeUtil;

    private final TestTubeConverter testTubeConverter;

    @Override
    public List<TestTubeItemDto> findAll() {
        return testTubeItemRepository.findAll().stream().map(testTubeMapper::fromModel).toList();
    }

    @Transactional
    @Override
    public List<TestTubeResultOrderDto> create(Long orderResultId, List<Long> testTubeItemIdList) {
        List<TestTubeResultOrderDto> testTubeResultOrderDtoList = new ArrayList<>();

        List<TestTubeItem> testTubeItemList = testTubeItemRepository.findByIds(testTubeItemIdList);

        for (TestTubeItem tubeItem : testTubeItemList) {
            TestTubeResult testTubeResult = new TestTubeResult();
            testTubeResult.setTestTubeItemId(tubeItem.getId());
            testTubeResult.setStatusId(TestTubeStatus.DIVISION);
            testTubeResult.setBarcode(barcodeService.generateBarcode());
            testTubeResult.setOrderResultId(orderResultId);
            testTubeResult.setTakeTestTime(dateTimeUtil.now());
            testTubeResult.setDisposalTime(dateTimeUtil.plusDay(testTubeResult.getTakeTestTime(), DEFAULT_DISPOSAL_DAY));

            testTubeResultRepository.create(testTubeResult);

            TestTubeItemDto testTubeItemDto = testTubeMapper.fromModel(tubeItem);
            TestTubeResultOrderDto testTubeResultOrderDto = testTubeMapper.fromModel(testTubeResult, testTubeItemDto, null);
            testTubeResultOrderDtoList.add(testTubeResultOrderDto);
        }

        return testTubeResultOrderDtoList;
    }

    @Override
    public List<TestTubeResultNurseDto> findByOrderId(Long orderId) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setOrderId(orderId);

        List<TestTubeResult> testTubeResultList = findTestTubeResultByParam(testTubeResultSearch);
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
        testTubeResultRepository.updateStatusByOrder(orderId, statusId);
    }

    @Override
    public TestTubeResultDto findById(Long id) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setId(id);

        List<TestTubeResult> testTubeResultList = findTestTubeResultByParam(testTubeResultSearch);
        TestTubeResult testTubeResult = testTubeResultList.get(0);

        return testTubeMapper.fromModel(testTubeResult, new TestTubeItemDto(), new TestTubeErrorDto(), "");
    }

    @Transactional
    @Override
    public void recordArrivalTestTubeAtLaboratory(String barcode) {
        testTubeResultRepository.updateStatusByBarcode(barcode, TestTubeStatus.LABORATORY);
        // create measurement
        // send message to analyzer
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

    private List<TestTubeResult> findTestTubeResultByParam(TestTubeResultSearch testTubeResultSearch) {
        StringBuilder stringBuilder = new StringBuilder();

        if (testTubeResultSearch.getId() != null) {
            stringBuilder.append(" AND id= #{search.id}");
        }

        if (testTubeResultSearch.getOrderId() != null) {
            stringBuilder.append(" AND order_result_id= #{search.orderId}");
        }

        if (stringBuilder.isEmpty()) {
            throw new BadSearchParamException("Incorrect test tube search data");
        }

        var testTubeResultList = testTubeResultRepository.findByParam(testTubeResultSearch, stringBuilder.toString());

        if (testTubeResultList == null || testTubeResultList.isEmpty()) {
            throw new NotFoundException("Test tube not found by param %s"
                    .formatted(testTubeConverter.testTubeResultSearchToString(testTubeResultSearch)));
        }

        return testTubeResultList;
    }
}
