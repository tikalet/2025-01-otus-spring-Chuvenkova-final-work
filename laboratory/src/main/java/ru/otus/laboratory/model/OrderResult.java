package ru.otus.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResult {

    private long id;

    private Integer price;

    private int statusId;

    private String paymentTime;

    private Long staffId;

    private Patient patient;

    private List<TestResult> testResultList;
}
