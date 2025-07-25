package ru.otus.laboratory.dto;

import lombok.Data;

@Data
public class TestResultDto {

    private long id;

    private long orderResultId;

    private String status;

    private String testItem;

}
