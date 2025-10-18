package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class TestTubeStatus {

    public static final int DIVISION = 1;

    public static final int TRANSPORTATION = 2;

    public static final int LABORATORY = 3;

    public static final int IN_WORK = 4;

    public static final int COMPLETED = 5;

    public static final int ARCHIVE = 6;

    public static final int DISPOSED = 7;

    public static final int ERROR = 8;

    private int id;

    private String name;
}
