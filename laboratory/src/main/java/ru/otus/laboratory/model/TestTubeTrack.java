package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class TestTubeTrack {

    private long id;

    private long testTubeResultId;

    private Long staffId;

    private Integer statusIdOlId;

    private Integer statusIdNewId;

    private Integer testTubeErrorId;

    private String changedTime;
}
