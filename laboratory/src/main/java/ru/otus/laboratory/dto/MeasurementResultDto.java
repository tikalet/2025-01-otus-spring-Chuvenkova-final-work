package ru.otus.laboratory.dto;

import lombok.Data;

@Data
public class MeasurementResultDto {

    private long id;

    private long testResultId;

    private MeasurementItemDto measurementItem;

    private Double value;

    private String measurTime;

}
