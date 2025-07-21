package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResultDto {

    private long id;

    private Integer price;

    private int statusId;

    private String paymentTime;

    private PatientDto patient;

    private StaffDto staff;
    
}
