package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeResultDto {

    private long id;

    private long orderResultId;

    private String status;

    private String barcode;

    private String testTubeItem;

    private String error;

    private String takeTestTime;

    private String disposalTime;

    private List<TestResultDto> testResultList;

    private List<TestTubeTrackDto> testTubeTrackList;
}
