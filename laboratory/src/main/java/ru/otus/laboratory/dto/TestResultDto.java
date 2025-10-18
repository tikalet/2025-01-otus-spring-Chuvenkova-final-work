package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {

    private long id;

    private long orderResultId;

    private String status;

    private TestItemDto testItem;

}
