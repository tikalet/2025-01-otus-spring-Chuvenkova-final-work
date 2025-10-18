package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementResultDto {

    private long id;

    private long testResultId;

    private MeasurementItemDto measurementItem;

    private Double value;

    private String measurTime;

}
