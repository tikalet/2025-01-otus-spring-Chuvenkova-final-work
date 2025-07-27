package ru.otus.laboratory.dto.adapter;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AdapterTaskDto {

    @NotBlank
    private String barcode;

    private List<String> extcodes;
}
