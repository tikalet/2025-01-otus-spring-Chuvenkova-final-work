package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class MeasurementItem {

    private long id;

    private String name;

    private String unit;

    private double min;

    private double max;

    private String extcode;
}
