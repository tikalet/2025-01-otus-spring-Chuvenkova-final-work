package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class MeasurementResult {

    private long id;

    private long testResultId;

    private long measurementItemId;

    private Double value;

    private String measurTime;

    private Long analyzerId;

    private long patientId;
}
