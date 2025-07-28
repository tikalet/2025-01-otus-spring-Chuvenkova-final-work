package ru.otus.laboratory.model.search;

import lombok.Data;

@Data
public class MeasurementResultSearch {

    private Long orderId;

    private Long patientId;

    private Long measurementItemId;

    private String barcode;
}
