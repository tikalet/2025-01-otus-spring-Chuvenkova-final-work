package ru.otus.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;
    
}
