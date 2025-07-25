package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResultDto {

    private long id;

    private Integer price;

    private String status;

    private String paymentTime;

    private PatientDto patient;

    private StaffDto staff;

    private List<TestResultOrderDto> testResultList;

}
