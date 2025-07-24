package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.converter.TestTubeConverter;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.exceptions.BadSearchParamException;
import ru.otus.laboratory.exceptions.InvalidStatusException;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeItem;
import ru.otus.laboratory.model.TestTubeResult;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.model.search.TestTubeResultSearch;
import ru.otus.laboratory.repository.TestTubeResultRepository;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestTubeResultServiceImpl implements TestTubeResultService {

    private static final int DEFAULT_DISPOSAL_DAY = 7;

    private final TestTubeResultRepository testTubeResultRepository;

    private final BarcodeService barcodeService;

    private final DateTimeUtil dateTimeUtil;

    private final TestTubeConverter testTubeConverter;

    private final DictService dictService;

    private final TestTubeItemService testTubeItemService;

    private final TestTubeMapper testTubeMapper;

    @Transactional
    @Override
    public List<TestTubeResultOrderDto> create(Long orderResultId, List<Long> testTubeItemIdList) {
        List<TestTubeResultOrderDto> testTubeResultOrderDtoList = new ArrayList<>();

        for (Long tubeItemId : testTubeItemIdList) {
            TestTubeItemDto testTubeItem = testTubeItemService.findById(tubeItemId);

            TestTubeResult testTubeResult = new TestTubeResult();
            testTubeResult.setTestTubeItemId(testTubeItem.getId());
            testTubeResult.setStatusId(TestTubeStatus.DIVISION);
            testTubeResult.setBarcode(barcodeService.generateBarcode());
            testTubeResult.setOrderResultId(orderResultId);
            testTubeResult.setTakeTestTime(dateTimeUtil.now());
            testTubeResult.setDisposalTime(dateTimeUtil.plusDay(testTubeResult.getTakeTestTime(), DEFAULT_DISPOSAL_DAY));

            testTubeResultRepository.create(testTubeResult);

            TestTubeResultOrderDto testTubeResultOrderDto = testTubeMapper.fromModel(testTubeResult, testTubeItem,
                    dictService.findTestTubeStatusById(testTubeResult.getStatusId()).getName(),
                    null);
            testTubeResultOrderDtoList.add(testTubeResultOrderDto);
        }

        return testTubeResultOrderDtoList;
    }

    @Override
    public List<TestTubeResultNurseDto> findByOrderId(Long orderId) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setOrderId(orderId);

        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);

        List<TestTubeResultNurseDto> testTubeResultNurseDtoList = new ArrayList<>();

        for (TestTubeResult testTubeResult : testTubeResultList) {
            testTubeResultNurseDtoList.add(testTubeMapper.fromModel(testTubeResult,
                    testTubeItemService.findById(testTubeResult.getTestTubeItemId()).getName()));
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
        return findTestTubeResultDto(testTubeResultSearch);
    }

    @Override
    public TestTubeResultDto findByBarcode(String barcode) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setBarcode(barcode);
        return findTestTubeResultDto(testTubeResultSearch);
    }

    @Transactional
    @Override
    public void recordArrivalTestTubeAtLaboratory(String barcode) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setBarcode(barcode);

        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);
        TestTubeResult testTubeResult = testTubeResultList.get(0);

        if (testTubeResult.getStatusId() >= TestTubeStatus.LABORATORY) {
            throw new InvalidStatusException("The test tube with barcode %s is already registered in the system"
                    .formatted(barcode));
        }

        testTubeResultRepository.updateStatus(testTubeResult.getId(), TestTubeStatus.LABORATORY);
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

    private List<TestTubeResult> findTestTubeResultsByParam(TestTubeResultSearch testTubeResultSearch) {
        StringBuilder stringBuilder = createSearchConditionFroTestTubeResult(testTubeResultSearch);

        var testTubeResultList = testTubeResultRepository.findByParam(testTubeResultSearch, stringBuilder.toString());

        if (testTubeResultList == null || testTubeResultList.isEmpty()) {
            throw new NotFoundException("Test tube not found with%s"
                    .formatted(testTubeConverter.testTubeResultSearchToString(testTubeResultSearch)));
        }

        return testTubeResultList;
    }

    private TestTubeResultDto findTestTubeResultDto(TestTubeResultSearch testTubeResultSearch) {
        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);
        TestTubeResult testTubeResult = testTubeResultList.get(0);

        TestTubeError testTubeError = dictService.findTestTubeErrorById(testTubeResult.getTestTubeErrorId());

        return testTubeMapper.fromModel(testTubeResult,
                testTubeItemService.findById(testTubeResult.getTestTubeItemId()).getName(),
                dictService.findTestTubeStatusById(testTubeResult.getStatusId()).getName(),
                testTubeError != null ? testTubeError.getName() : null,
                "");
    }

    private StringBuilder createSearchConditionFroTestTubeResult(TestTubeResultSearch testTubeResultSearch) {
        StringBuilder stringBuilder = new StringBuilder();

        if (testTubeResultSearch.getId() != null) {
            stringBuilder.append(" AND id= #{search.id}");
        }

        if (testTubeResultSearch.getOrderId() != null) {
            stringBuilder.append(" AND order_result_id= #{search.orderId}");
        }

        if (testTubeResultSearch.getBarcode() != null && !testTubeResultSearch.getBarcode().isEmpty()) {
            stringBuilder.append(" AND barcode= #{search.barcode}");
        }

        if (stringBuilder.isEmpty()) {
            throw new BadSearchParamException("Incorrect test tube search data");
        }
        return stringBuilder;
    }
}
