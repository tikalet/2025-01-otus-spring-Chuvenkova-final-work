package ru.otus.laboratory.dto;

import lombok.Data;

@Data
public class MeasurementItemDto {

    private long id;

    private String name;

    private String unit;

    private double min;

    private double max;

    private String extcode;
}
