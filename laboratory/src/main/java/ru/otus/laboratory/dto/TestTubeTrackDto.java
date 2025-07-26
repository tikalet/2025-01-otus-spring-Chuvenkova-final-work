package ru.otus.laboratory.dto;

import lombok.Data;

@Data
public class TestTubeTrackDto {

    private long id;

    private long testTubeResultId;

    private StaffDto staff;

    private String statusOld;

    private String statusNew;

    private String testTubeError;

    private String changedTime;
}
