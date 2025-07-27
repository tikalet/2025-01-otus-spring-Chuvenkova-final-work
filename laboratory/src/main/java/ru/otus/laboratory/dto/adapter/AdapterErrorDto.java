package ru.otus.laboratory.dto.adapter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdapterErrorDto {

    @NotBlank
    private String barcode;

    @NotNull
    private Integer errorId;
}
