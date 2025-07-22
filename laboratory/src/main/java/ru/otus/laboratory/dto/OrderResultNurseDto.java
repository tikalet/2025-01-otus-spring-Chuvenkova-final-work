package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResultNurseDto {

    private long id;

    private String patientName;

    private List<TestTubeResultNurseDto> testTubeResultList;
}
