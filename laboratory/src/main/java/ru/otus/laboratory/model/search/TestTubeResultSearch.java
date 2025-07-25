package ru.otus.laboratory.model.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestTubeResultSearch {

    private Long id;

    private Long orderId;

    private String barcode;

    private String time;
    
    private Long statusId;
}
