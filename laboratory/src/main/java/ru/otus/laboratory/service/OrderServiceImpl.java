package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.OrderResultNurseDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.exceptions.BadSearchParamException;
import ru.otus.laboratory.exceptions.InvalidStatusException;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.OrderMapper;
import ru.otus.laboratory.mapper.PatientMapper;
import ru.otus.laboratory.model.OrderResult;
import ru.otus.laboratory.model.OrderStatus;
import ru.otus.laboratory.model.TestStatus;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.model.search.OrderSearch;
import ru.otus.laboratory.repository.OrderRepository;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final PatientMapper patientMapper;

    private final DateTimeUtil dateTimeUtil;

    private final OrderRepository orderRepository;

    private final TestResultService testResultService;

    private final TestItemService testItemService;

    private final PatientService patientService;

    private final StaffService staffService;

    private final TestTubeResultService testTubeResultService;

    private final DictService dictService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderResultDto create(OrderResultCreateDto orderResultCreateDto) {
        PatientDto patientDto = patientService.findById(orderResultCreateDto.getPatientId());
        StaffDto staffDto = staffService.findById(orderResultCreateDto.getStaffId());

        var testItemList = fillTestItemForOrder(orderResultCreateDto.getTestItemIdList());

        OrderResult orderResult = new OrderResult();
        orderResult.setStatusId(OrderStatus.CREATE);
        orderResult.setPatient(patientMapper.toModel(patientDto));
        orderResult.setStaffId(orderResultCreateDto.getStaffId());
        orderResult.setPrice(calcTotalSum(testItemList));
        orderResult.setPaymentTime(dateTimeUtil.now());
        orderRepository.create(orderResult);

        var testTubeResultDtoList = testTubeResultService.create(orderResult.getId(), orderResultCreateDto.getStaffId(),
                calcTestTubeItemIdList(testItemList));
        var testResultDtoList = testResultService.create(orderResult.getId(), orderResult.getStaffId(), testItemList,
                testTubeResultDtoList);

        OrderResultDto orderResultDto = orderMapper.fromModel(orderResult, patientDto, staffDto,
                dictService.findOrderStatusById(orderResult.getStatusId()).getName());
        orderResultDto.setTestResultList(testResultDtoList);
        return orderResultDto;
    }

    @Override
    public List<OrderResultNurseDto> findOrderForNurse() {
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setTime(dateTimeUtil.nowDate());
        orderSearch.setStatusId(OrderStatus.CREATE);

        List<OrderResult> orderResultList = orderRepository.findByParam(orderSearch,
                createSearchConditionForResult(orderSearch));
        return orderResultList.stream()
                .map(orderResult -> orderMapper.fromModel(orderResult, null))
                .toList();
    }

    @Override
    public OrderResultNurseDto findOrderForNurseById(Long id) {
        OrderResult orderResult = findOrderById(id);

        if (orderResult.getStatusId() > OrderStatus.CREATE) {
            throw new InvalidStatusException("Order with id %d cannot be reopened".formatted(id));
        }

        List<TestTubeResultNurseDto> testTubeResultNurseDtoList = testTubeResultService.findByOrderId(id);
        return orderMapper.fromModel(orderResult, testTubeResultNurseDtoList);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderForNurse(Long id, Long staffId) {
        OrderResult orderResult = findOrderById(id);

        if (orderResult.getStatusId() > OrderStatus.CREATE) {
            throw new InvalidStatusException("To pick up the biomaterial for the order with %d".formatted(id));
        }

        orderRepository.updateStatus(id, OrderStatus.IN_WORK);
        testResultService.updateStatusByOrderId(id, TestStatus.IN_WORK);
        testTubeResultService.updateStatusByOrder(id, staffId, TestTubeStatus.TRANSPORTATION);
    }

    @Override
    public List<OrderResultDto> findOrderByPatientId(Long patientId) {
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setPatientId(patientId);

        List<OrderResult> orderResultList = orderRepository.findByParam(orderSearch,
                createSearchConditionForResult(orderSearch));

        if (orderResultList == null || orderResultList.isEmpty()) {
            throw new NotFoundException("Order with patientId %d not found".formatted(patientId));
        }

        return orderResultList.stream()
                .map(orderResult -> orderMapper.fromModel(orderResult,
                        patientMapper.fromModel(orderResult.getPatient()),
                        null,
                        dictService.findOrderStatusById(orderResult.getStatusId()).getName()))
                .toList();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void signTestTubeDocument(String barcode) {
        TestTubeResultDto testTubeResult = testTubeResultService.findByBarcode(barcode);
        testResultService.updateStatusByTestTubeResultId(testTubeResult.getId(), TestStatus.READY);

        if (testResultService.allTestReady(testTubeResult.getOrderResultId())) {
            orderRepository.updateStatus(testTubeResult.getOrderResultId(), OrderStatus.COMPLETED);
        }
    }

    private Integer calcTotalSum(List<TestItemDto> testItemList) {
        return testItemList.stream().mapToInt(TestItemDto::getPrice).sum();
    }

    private List<Long> calcTestTubeItemIdList(List<TestItemDto> testItemList) {
        return testItemList.stream().map(TestItemDto::getTestTubeId).collect(Collectors.toList());
    }

    private OrderResult findOrderById(Long id) {
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setId(id);

        List<OrderResult> orderResultList = orderRepository.findByParam(orderSearch,
                createSearchConditionForResult(orderSearch));

        if (orderResultList == null || orderResultList.isEmpty()) {
            throw new NotFoundException("Order with id %d not found".formatted(id));
        }

        return orderResultList.get(0);
    }

    private List<TestItemDto> fillTestItemForOrder(List<Long> testItemIdList) {
        List<TestItemDto> testItemDtoList = new ArrayList<>();

        for (Long id : testItemIdList) {
            testItemDtoList.add(testItemService.findById(id));
        }

        return testItemDtoList;
    }

    private String createSearchConditionForResult(OrderSearch orderSearch) {
        StringBuilder stringBuilder = new StringBuilder();

        if (orderSearch.getPatientId() != null) {
            stringBuilder.append(" AND ord.patient_id= #{search.patientId}");
        }

        if (orderSearch.getTime() != null && !orderSearch.getTime().isEmpty()) {
            stringBuilder.append(" AND ord.payment_time LIKE #{search.time} || '%'");
        }

        if (orderSearch.getStatusId() != null) {
            stringBuilder.append(" AND ord.status_id= #{search.statusId}");
        }

        if (orderSearch.getId() != null) {
            stringBuilder.append(" AND ord.id= #{search.id}");
        }

        if (stringBuilder.isEmpty()) {
            throw new BadSearchParamException("Incorrect order search data");
        }
        return stringBuilder.toString();
    }
}
