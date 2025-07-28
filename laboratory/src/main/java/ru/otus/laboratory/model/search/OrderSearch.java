package ru.otus.laboratory.model.search;

import lombok.Data;

@Data
public class OrderSearch {

    private String time;

    private Integer statusId;

    private Long patientId;

    private Long id;
}
