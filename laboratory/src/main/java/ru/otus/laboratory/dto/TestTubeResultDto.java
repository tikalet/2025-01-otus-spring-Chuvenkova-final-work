package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeResultDto {

    private long id;

    private int statusId;

    private String barcode;

    private TestTubeItemDto testTubeItem;

    private TestTubeErrorDto testTubeError;
}
