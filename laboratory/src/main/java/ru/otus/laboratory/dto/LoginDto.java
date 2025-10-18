package ru.otus.laboratory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
