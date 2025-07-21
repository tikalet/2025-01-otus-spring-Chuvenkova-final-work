package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.mapper.OrderMapper;
import ru.otus.laboratory.model.OrderResult;
import ru.otus.laboratory.model.TestItem;
import ru.otus.laboratory.repository.OrderRepository;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final DateTimeUtil dateTimeUtil;

    private final OrderRepository orderRepository;

    private final TestService testService;

    private final PatientService patientService;

    private final StaffService staffService;

    private final TestTubeService testTubeService;

    @Transactional
    @Override
    public OrderResultDto create(OrderResultCreateDto orderResultCreateDto) {
        PatientDto patientDto = patientService.findById(orderResultCreateDto.getPatientId());
        StaffDto staffDto = staffService.findById(orderResultCreateDto.getStaffId());

        var testItemList = testService.findByIds(orderResultCreateDto.getTestItemIdList());

        OrderResult orderResult = orderMapper.toModel(orderResultCreateDto);
        orderResult.setPrice(calcTotalSum(testItemList));
        orderResult.setPaymentTime(dateTimeUtil.now());
        orderRepository.create(orderResult);

        var testResultDtoList = testService.create(orderResult.getId(), testItemList);
        var testTubeResultDtoList = testTubeService.create(orderResult.getId(), testItemList, testResultDtoList);

        return orderMapper.fromModel(orderResult, patientDto, staffDto);
    }

    private Integer calcTotalSum(List<TestItem> testItemList) {
        return testItemList.stream().mapToInt(TestItem::getPrice).sum();
    }
}
