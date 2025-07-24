package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {

    private long id;

    private int price;

    private long orderResultId;

    private long staffId;

    private String status;

    private TestItemDto testItem;

    private TestTubeResultOrderDto testTubeResult;
}
