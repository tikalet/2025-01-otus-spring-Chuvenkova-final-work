package ru.otus.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeResult {

    private long id;

    private long orderResultId;

    private int statusId;

    private String barcode;

    private TestTubeItem testTubeItem;

    private List<TestResult> testResultList;
}
