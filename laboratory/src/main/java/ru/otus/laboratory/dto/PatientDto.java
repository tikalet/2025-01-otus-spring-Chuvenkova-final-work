package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String birthday;

    private String phone;

    private String email;
}
