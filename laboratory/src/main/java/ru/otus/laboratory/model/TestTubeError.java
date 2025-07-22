package ru.otus.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeError {

    private int id;

    private String name;

    private boolean needNotifyPatient;
}
