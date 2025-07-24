package ru.otus.laboratory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestTubeResultErrorUpdateDto {

    @NotNull
    private Long id;

    private Integer errorId;
}
