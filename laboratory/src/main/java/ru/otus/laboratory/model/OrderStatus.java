package ru.otus.laboratory.model;

import lombok.Data;

@Data
public class OrderStatus {

    public static final int CREATE = 1;

    public static final int IN_WORK = 2;

    public static final int COMPLETED = 3;

    public static final int CANCELED = 4;

    private int id;

    private String name;
}
