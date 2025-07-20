package ru.otus.laboratory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientCreateDto {

    @NotBlank
    @Size(min = 1, message = "The name must contain at least one character.")
    @Pattern.List({
            @Pattern(regexp = "^[\\p{Alpha}]*$", message = "Name should contain only alphabets"),
            @Pattern(regexp = "^[^a-z].*$", message = "Name should not start with a lower case character"),
            @Pattern(regexp = ".[^A-Z].*$", message = "Name should not contain any capital letters except the first one")
    })
    private String firstName;

    @NotBlank
    @Size(min = 1, message = "The name must contain at least one character.")
    @Pattern.List({
            @Pattern(regexp = "^[\\p{Alpha}]*$", message = "Name should contain only alphabets"),
            @Pattern(regexp = "^[^a-z].*$", message = "Name should not start with a lower case character"),
            @Pattern(regexp = ".[^A-Z].*$", message = "Name should not contain any capital letters except the first one")
    })
    private String middleName;

    @NotBlank
    @Size(min = 1, message = "The name must contain at least one character.")
    @Pattern.List({
            @Pattern(regexp = "^[\\p{Alpha}]*$", message = "Name should contain only alphabets"),
            @Pattern(regexp = "^[^a-z].*$", message = "Name should not start with a lower case character"),
            @Pattern(regexp = ".[^A-Z].*$", message = "Name should not contain any capital letters except the first one")
    })
    private String lastName;

    @NotBlank
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
    private String birthday;

    @NotBlank
    private String phone;

    private String email;
}
