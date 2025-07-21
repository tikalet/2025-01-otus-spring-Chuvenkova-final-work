package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.model.OrderResult;
import ru.otus.laboratory.model.OrderStatus;

@Component
public class OrderMapper {

    public OrderResultDto fromModel(OrderResult orderResult, PatientDto patientDto, StaffDto staffDto) {
        OrderResultDto orderResultDto = new OrderResultDto();
        orderResultDto.setId(orderResult.getId());
        orderResultDto.setPrice(orderResult.getPrice());
        orderResultDto.setPaymentTime(orderResult.getPaymentTime());
        orderResultDto.setStatusId(orderResult.getStatusId());
        orderResultDto.setStaff(staffDto);
        orderResultDto.setPatient(patientDto);
        return orderResultDto;
    }

    public OrderResult toModel(OrderResultDto orderResultDto) {
        OrderResult orderResult = new OrderResult();
        return orderResult;
    }

    public OrderResult toModel(OrderResultCreateDto orderResultCreateDto) {
        OrderResult orderResult = new OrderResult();
        orderResult.setStatusId(OrderStatus.CREATE);
        orderResult.setPatientId(orderResultCreateDto.getPatientId());
        orderResult.setStaffId(orderResultCreateDto.getStaffId());
        return orderResult;
    }

}
