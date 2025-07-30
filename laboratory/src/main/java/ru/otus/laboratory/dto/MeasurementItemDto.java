package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementItemDto {

    private long id;

    private String name;

    private String unit;

    private double min;

    private double max;

    private String extcode;
}
