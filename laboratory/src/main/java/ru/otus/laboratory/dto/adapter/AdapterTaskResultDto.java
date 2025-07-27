package ru.otus.laboratory.dto.adapter;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdapterTaskResultDto {

    @NotBlank
    private String extcode;

    private Double value;
}
