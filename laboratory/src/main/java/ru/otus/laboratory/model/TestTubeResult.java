package ru.otus.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeResult {

    private long id;

    private long orderResultId;

    private int statusId;

    private String barcode;

    private long testTubeItemId;

    private Long testTubeErrorId;
}
