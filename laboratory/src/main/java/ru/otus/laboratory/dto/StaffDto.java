package ru.otus.laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;
}
