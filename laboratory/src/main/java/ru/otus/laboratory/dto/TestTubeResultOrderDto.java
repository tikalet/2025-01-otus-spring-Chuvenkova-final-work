package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeResultOrderDto {

    private long id;

    private String status;

    private String barcode;

    private TestTubeItemDto testTubeItem;

    private String error;
}
