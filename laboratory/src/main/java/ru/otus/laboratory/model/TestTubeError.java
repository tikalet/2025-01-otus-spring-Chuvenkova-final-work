package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class TestTubeError {

    private int id;

    private String name;

    private boolean needNotifyPatient;
}
