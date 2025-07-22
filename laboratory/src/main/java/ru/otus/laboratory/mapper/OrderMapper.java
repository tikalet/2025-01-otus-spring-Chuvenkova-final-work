package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.OrderResultNurseDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.model.OrderResult;

import java.util.List;

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

    public OrderResultNurseDto fromModel(OrderResult orderResult,
                                         List<TestTubeResultNurseDto> testTubeResultNurseDtoList) {
        OrderResultNurseDto orderResultDto = new OrderResultNurseDto();
        orderResultDto.setId(orderResult.getId());
        orderResultDto.setPatientName(orderResult.getPatient().getLastName()
                + " " + orderResult.getPatient().getFirstName()
                + " " + orderResult.getPatient().getMiddleName());
        orderResultDto.setTestTubeResultList(testTubeResultNurseDtoList);
        return orderResultDto;
    }


}
