package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class TestTubeError {

    private Integer id;

    private String text;

    private boolean needNotifyPatient;
}
