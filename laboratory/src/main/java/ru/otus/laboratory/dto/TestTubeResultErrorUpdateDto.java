package ru.otus.laboratory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestTubeResultErrorUpdateDto {

    @NotNull
    private Long id;

    private Integer errorId;
}
