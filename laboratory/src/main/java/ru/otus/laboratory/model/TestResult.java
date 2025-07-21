package ru.otus.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {

    private long id;

    private int price;

    private long orderResultId;

    private long testItemId;

    private long staffId;

    private int statusId;
}
