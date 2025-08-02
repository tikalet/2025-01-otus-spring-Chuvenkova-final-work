package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.converter.TestTubeConverter;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultErrorUpdateDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.dto.TestTubeTrackDto;
import ru.otus.laboratory.exceptions.BadSearchParamException;
import ru.otus.laboratory.exceptions.InvalidStatusException;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.TestTubeMapper;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeResult;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.model.TestTubeTrack;
import ru.otus.laboratory.model.search.TestTubeResultSearch;
import ru.otus.laboratory.repository.TestTubeResultRepository;
import ru.otus.laboratory.repository.TestTubeTrackRepository;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestTubeResultServiceImpl implements TestTubeResultService {

    private static final int DEFAULT_DISPOSAL_DAY = 7;

    private final TestTubeResultRepository testTubeResultRepository;

    private final TestTubeMapper testTubeMapper;

    private final DateTimeUtil dateTimeUtil;

    private final TestTubeConverter testTubeConverter;

    private final BarcodeService barcodeService;

    private final DictService dictService;

    private final TestTubeItemService testTubeItemService;

    private final TestResultService testResultService;

    private final MeasurementResultService measurementResultService;

    private final TestTubeTrackRepository testTubeTrackRepository;

    private final StaffService staffService;

    @Transactional
    @Override
    public List<TestTubeResultOrderDto> create(Long orderResultId, Long staffId, List<Long> testTubeItemIdList) {
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
                    dictService.findTestTubeStatusById(testTubeResult.getStatusId()).getName(), null);
            testTubeResultOrderDtoList.add(testTubeResultOrderDto);

            createTrack(testTubeResult.getId(), staffId, null, testTubeResult.getStatusId(), null);
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
    public void updateStatusByOrder(long orderId, Long staffId, int statusId) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setOrderId(orderId);
        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);

        for (TestTubeResult testTubeResult : testTubeResultList) {
            createTrack(testTubeResult.getId(), staffId, testTubeResult.getStatusId(), statusId, null);
            testTubeResultRepository.updateStatus(testTubeResult.getId(), statusId);
        }
    }

    @Transactional
    @Override
    public void updateStatusByBarcode(String barcode, Long staffId, int statusId) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setBarcode(barcode);
        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);

        for (TestTubeResult testTubeResult : testTubeResultList) {
            createTrack(testTubeResult.getId(), staffId, testTubeResult.getStatusId(), statusId, null);
            testTubeResultRepository.updateStatus(testTubeResult.getId(), statusId);
        }
    }

    @Override
    public TestTubeResultDto findById(Long id) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setId(id);
        return prepareTestTubeResultDto(testTubeResultSearch);
    }

    @Override
    public TestTubeResultDto findByBarcode(String barcode) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setBarcode(barcode);
        return prepareTestTubeResultDto(testTubeResultSearch);
    }

    @Override
    public List<TestTubeResultDto> findByTimeAndStatus(String time, Long statusId) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setTime(time);
        testTubeResultSearch.setStatusId(statusId);

        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);
        List<TestTubeResultDto> testTubeResultDtoList = new ArrayList<>();

        for (TestTubeResult testTubeResult : testTubeResultList) {
            TestTubeError testTubeError = dictService.findTestTubeErrorById(testTubeResult.getTestTubeErrorId());

            TestTubeResultDto testTubeResultDto = testTubeMapper.fromModel(testTubeResult,
                    testTubeItemService.findById(testTubeResult.getTestTubeItemId()).getName(),
                    dictService.findTestTubeStatusById(testTubeResult.getStatusId()).getName(),
                    testTubeError != null ? testTubeError.getText() : null,
                    testResultService.findByTestTubeResultId(testTubeResult.getId()), null);

            testTubeResultDtoList.add(testTubeResultDto);
        }

        return testTubeResultDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void recordArrivalTestTubeAtLaboratory(String barcode, Long staffId) {
        TestTubeResult testTubeResult = findTestTubeByBarcode(barcode);

        if (testTubeResult.getStatusId() >= TestTubeStatus.LABORATORY) {
            throw new InvalidStatusException("The test tube with barcode %s is already registered in the system"
                    .formatted(barcode));
        }

        createTrack(testTubeResult.getId(), staffId, testTubeResult.getStatusId(), TestTubeStatus.LABORATORY, null);
        testTubeResultRepository.updateStatus(testTubeResult.getId(), TestTubeStatus.LABORATORY);

        Long patientId = testTubeResultRepository.findPatientByBarcode(barcode);

        List<TestResultDto> testResultDtoList = testResultService.findByTestTubeResultId(testTubeResult.getId());
        measurementResultService.create(testResultDtoList, patientId);
    }

    private TestTubeResult findTestTubeByBarcode(String barcode) {
        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setBarcode(barcode);

        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);
        return testTubeResultList.get(0);
    }

    @Transactional
    @Override
    public TestTubeResultDto updateErrorInfo(TestTubeResultErrorUpdateDto errorUpdateDto) {
        TestTubeError testTubeError = getTestTubeError(errorUpdateDto.getErrorId());

        TestTubeResultSearch testTubeResultSearch = new TestTubeResultSearch();
        testTubeResultSearch.setId(errorUpdateDto.getId());
        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);
        TestTubeResult testTubeResult = testTubeResultList.get(0);

        int statusNew = errorUpdateDto.getErrorId() == null ?
                caclPreviousNoErrorStatus(testTubeResult.getId()) : TestTubeStatus.ERROR;

        createTrack(testTubeResult.getId(), errorUpdateDto.getStaffId(), testTubeResult.getStatusId(), statusNew,
                errorUpdateDto.getErrorId());

        testTubeResult.setStatusId(statusNew);
        testTubeResult.setTestTubeErrorId(errorUpdateDto.getErrorId());
        testTubeResultRepository.update(testTubeResult);

        List<TestResultDto> testResultDtoList = testResultService.findByTestTubeResultId(testTubeResult.getId());
        return testTubeMapper.fromModel(testTubeResult,
                testTubeItemService.findById(testTubeResult.getTestTubeItemId()).getName(),
                dictService.findTestTubeStatusById(testTubeResult.getStatusId()).getName(),
                testTubeError != null ? testTubeError.getText() : null, testResultDtoList, null);
    }

    @Transactional
    @Override
    public void updateErrorInfo(String barcode, Integer errorId, Long staffId) {
        TestTubeResult testTubeResult = findTestTubeByBarcode(barcode);
        TestTubeError testTubeError = getTestTubeError(errorId);

        int statusNew = TestTubeStatus.ERROR;
        createTrack(testTubeResult.getId(), staffId, testTubeResult.getStatusId(), statusNew, errorId);

        testTubeResult.setStatusId(statusNew);
        testTubeResult.setTestTubeErrorId(errorId);
        testTubeResultRepository.update(testTubeResult);
    }

    private TestTubeError getTestTubeError(Integer errorId) {
        TestTubeError testTubeError = dictService.findTestTubeErrorById(errorId);

        if (errorId != null && testTubeError == null) {
            throw new NotFoundException("Test tube error with id %d not found".formatted(errorId));
        }
        return testTubeError;
    }


    private List<TestTubeResult> findTestTubeResultsByParam(TestTubeResultSearch testTubeResultSearch) {
        String stringBuilder = createSearchConditionTestTubeResult(testTubeResultSearch);

        var testTubeResultList = testTubeResultRepository.findByParam(testTubeResultSearch, stringBuilder);

        if (testTubeResultList == null || testTubeResultList.isEmpty()) {
            throw new NotFoundException("Test tube not found with%s"
                    .formatted(testTubeConverter.testTubeResultSearchToString(testTubeResultSearch)));
        }

        return testTubeResultList;
    }

    private TestTubeResultDto prepareTestTubeResultDto(TestTubeResultSearch testTubeResultSearch) {
        List<TestTubeResult> testTubeResultList = findTestTubeResultsByParam(testTubeResultSearch);
        TestTubeResult testTubeResult = testTubeResultList.get(0);

        TestTubeError testTubeError = dictService.findTestTubeErrorById(testTubeResult.getTestTubeErrorId());

        List<TestResultDto> testResultDtoList = testResultService.findByTestTubeResultId(testTubeResult.getId());

        List<TestTubeTrack> tubeTrackList = testTubeTrackRepository.findByTestTubeResultId(testTubeResult.getId());
        List<TestTubeTrackDto> testTubeTrackDtoList = tubeTrackList.stream()
                .map(testTubeTrack -> testTubeMapper.fromModel(testTubeTrack,
                        testTubeTrack.getStaffId() != null ? staffService.findById(testTubeTrack.getStaffId()) : null)
                )
                .toList();

        return testTubeMapper.fromModel(testTubeResult,
                testTubeItemService.findById(testTubeResult.getTestTubeItemId()).getName(),
                dictService.findTestTubeStatusById(testTubeResult.getStatusId()).getName(),
                testTubeError != null ? testTubeError.getText() : null, testResultDtoList, testTubeTrackDtoList);
    }

    private String createSearchConditionTestTubeResult(TestTubeResultSearch testTubeResultSearch) {
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
        if (testTubeResultSearch.getTime() != null && !testTubeResultSearch.getTime().isEmpty()) {
            stringBuilder.append(" AND take_test_time LIKE #{search.time} || '%'");
        }
        if (testTubeResultSearch.getStatusId() != null) {
            stringBuilder.append(" AND status_id= #{search.statusId}");
        }

        if (stringBuilder.isEmpty()) {
            throw new BadSearchParamException("Incorrect test tube search data");
        }
        return stringBuilder.toString();
    }

    private void createTrack(Long testTubeResultId, Long staffId,
                             Integer statusOld, Integer statusNew, Integer errorId) {
        TestTubeTrack testTubeTrack = new TestTubeTrack();
        testTubeTrack.setTestTubeErrorId(errorId);
        testTubeTrack.setTestTubeResultId(testTubeResultId);
        testTubeTrack.setChangedTime(dateTimeUtil.now());
        testTubeTrack.setStatusIdNewId(statusNew);
        testTubeTrack.setStatusIdOlId(statusOld);
        testTubeTrack.setStaffId(staffId);

        testTubeTrackRepository.create(testTubeTrack);
    }

    private Integer caclPreviousNoErrorStatus(Long testTubeResultId) {
        List<TestTubeTrack> tubeTrackList = testTubeTrackRepository.findByTestTubeResultId(testTubeResultId);

        if (tubeTrackList == null || tubeTrackList.isEmpty()) {
            return TestTubeStatus.ERROR;
        }

        for (TestTubeTrack testTubeTrack : tubeTrackList) {
            if (testTubeTrack.getStatusIdOlId() == null || testTubeTrack.getStatusIdNewId() == null) {
                continue;
            }

            if (testTubeTrack.getStatusIdOlId() != TestTubeStatus.ERROR
                    && testTubeTrack.getStatusIdNewId() == TestTubeStatus.ERROR) {
                return testTubeTrack.getStatusIdOlId();
            }
        }

        return TestTubeStatus.ERROR;
    }
}
