package ru.otus.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AdapterTaskDto {

    @NotBlank
    private String barcode;

    @NotNull
    private List<@NotBlank String> extcodes;
}
