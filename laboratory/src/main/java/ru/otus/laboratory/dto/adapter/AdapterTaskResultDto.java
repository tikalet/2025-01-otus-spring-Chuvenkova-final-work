package ru.otus.laboratory.dto.adapter;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdapterTaskResultDto {

    @NotBlank
    private String extcode;

    private Double value;
}
